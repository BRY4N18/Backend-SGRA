package com.CLMTZ.Backend.service.academic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.CLMTZ.Backend.dto.academic.CoordinationDTO;
import com.CLMTZ.Backend.dto.academic.StudentLoadDTO;
import com.CLMTZ.Backend.dto.academic.TeachingDTO;
import com.CLMTZ.Backend.model.academic.Coordination;
import com.CLMTZ.Backend.repository.academic.ICareerRepository;
import com.CLMTZ.Backend.repository.academic.ICoordinationRepository;
import com.CLMTZ.Backend.repository.academic.IDataLoadRepository;
import com.CLMTZ.Backend.repository.general.IUserRepository;
import com.CLMTZ.Backend.service.academic.ICoordinationService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoordinationServiceImpl implements ICoordinationService {

    private final ICoordinationRepository repository;
    private final IUserRepository userRepository;
    private final ICareerRepository careerRepository;
    private final IDataLoadRepository dataLoadRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // --- MÉTODOS CRUD ---

    @Override
    public List<CoordinationDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CoordinationDTO findById(Integer id) {
        return repository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Coordination not found with id: " + id));
    }

    @Override
    public CoordinationDTO save(CoordinationDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    @Override
    public CoordinationDTO update(Integer id, CoordinationDTO dto) {
        Coordination entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordination not found with id: " + id));
        if (dto.getUserId() != null)
            entity.setUserId(userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found")));
        if (dto.getCareerId() != null)
            entity.setCareerId(careerRepository.findById(dto.getCareerId())
                    .orElseThrow(() -> new RuntimeException("Career not found")));
        return toDTO(repository.save(entity));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    // --- CARGA MASIVA ---

    @Override
    public List<String> uploadStudents(List<StudentLoadDTO> dtos) {
        List<String> resultados = new ArrayList<>();

        if (dtos == null || dtos.isEmpty()) {
            resultados.add("ADVERTENCIA: No se encontraron registros válidos en el archivo. Verifique que el Excel tenga el formato correcto (datos a partir de la fila 4).");
            return resultados;
        }

        for (StudentLoadDTO fila : dtos) {
            try {
                System.out.println("Procesando Identificación: " + fila.getIdentificacion());

                String resultadoSP = ejecutarCargaEstudianteSP(
                        fila.getIdentificacion(), fila.getNombres(), fila.getApellidos(),
                        fila.getCorreo(), fila.getTelefono());

                resultados.add("ID " + fila.getIdentificacion() + ": " + resultadoSP);

            } catch (Exception e) {
                resultados.add("ID " + fila.getIdentificacion() + ": ERROR (" + e.getMessage() + ")");
                e.printStackTrace();
            }
        }

        long exitosos = resultados.stream().filter(r -> r.endsWith(": OK")).count();
        long errores = resultados.size() - exitosos;
        resultados.add(0, "RESUMEN: " + dtos.size() + " registros procesados → " + exitosos + " exitosos, " + errores + " con errores/advertencias.");

        return resultados;
    }

    @Override
    public List<String> uploadTeachers(List<TeachingDTO> dtos) {
        List<String> resultados = new ArrayList<>();

        if (dtos == null || dtos.isEmpty()) {
            resultados.add("ADVERTENCIA: No se encontraron registros válidos en el archivo. Verifique que el Excel tenga el formato correcto (datos a partir de la fila 3).");
            return resultados;
        }

        // Periodo activo (lo gestiona el admin, no viene del Excel)
        Integer idPeriodo = dataLoadRepository.obtenerIdPeriodoActivo();
        if (idPeriodo == null) {
            resultados.add("ERROR GENERAL: No hay un periodo activo configurado en el sistema.");
            return resultados;
        }

        for (TeachingDTO fila : dtos) {
            String nombreRef = fila.getNombreCompleto() != null ? fila.getNombreCompleto() : fila.getApellidos();
            try {
                // 1. Buscar cédula del docente por apellidos y nombres
                //    (Docente.xls no tiene cédula, se busca por nombre en BD)
                String cedulaDocente = dataLoadRepository.obtenerCedulaDocente(
                        fila.getApellidos(), fila.getNombres());

                if (cedulaDocente == null || cedulaDocente.isEmpty()) {
                    resultados.add("Docente '" + nombreRef + "': ERROR (Docente no encontrado en BD. Verifique que esté registrado)");
                    continue;
                }

                // 2. Obtener ID de carrera (modalidad puede ser null, la BD la resuelve por carrera)
                Map<String, Object> ids = dataLoadRepository.obtenerIdsPorTexto(fila.getCarreraTexto(), null);

                if (ids == null || ids.get("id_carrera_encontrado") == null) {
                    resultados.add("Docente '" + nombreRef + "': ERROR (Carrera no encontrada: '" + fila.getCarreraTexto() + "')");
                    continue;
                }

                Integer idCarrera = (Integer) ids.get("id_carrera_encontrado");
                Integer idModalidad = ids.get("id_modalidad_encontrado") != null
                        ? (Integer) ids.get("id_modalidad_encontrado") : 1;

                // 3. Obtener ID de asignatura
                Integer idAsignatura = dataLoadRepository.obtenerIdAsignatura(fila.getAsignaturaTexto(), idCarrera);
                if (idAsignatura == null) {
                    resultados.add("Docente '" + nombreRef + "': ERROR (Asignatura '" + fila.getAsignaturaTexto() + "' no existe en esa carrera)");
                    continue;
                }

                // 4. Obtener ID de paralelo
                Integer idParalelo = dataLoadRepository.obtenerIdParalelo(fila.getParaleloTexto());
                if (idParalelo == null) {
                    resultados.add("Docente '" + nombreRef + "': ERROR (Paralelo '" + fila.getParaleloTexto() + "' inválido)");
                    continue;
                }

                // 5. Verificar si ya tiene esta clase asignada
                boolean claseOcupada = dataLoadRepository.validarDocenteConClase(
                        cedulaDocente, idAsignatura, idPeriodo, idParalelo);
                if (claseOcupada) {
                    resultados.add("Docente '" + nombreRef + "': ADVERTENCIA (Ya tiene esta clase asignada, se actualizará)");
                }

                // 6. Ejecutar SP (género no viene en el archivo, se usa 1 por defecto)
                String resultadoSP = ejecutarCargaDocenteSP(
                        cedulaDocente, fila.getNombres(), fila.getApellidos(),
                        fila.getCorreo() != null ? fila.getCorreo() : "",
                        "", fila.getTelefono() != null ? fila.getTelefono() : "",
                        idModalidad, 1, idPeriodo, idAsignatura, idParalelo);

                resultados.add("Docente '" + nombreRef + "': " + resultadoSP);

            } catch (Exception e) {
                resultados.add("Docente '" + nombreRef + "': ERROR INTERNO (" + e.getMessage() + ")");
                e.printStackTrace();
            }
        }

        long exitosos = resultados.stream().filter(r -> r.endsWith(": OK")).count();
        long errores = resultados.stream().filter(r -> r.contains(": ERROR")).count();
        resultados.add(0, "RESUMEN: " + dtos.size() + " registros procesados → " + exitosos + " exitosos, " + errores + " con errores.");

        return resultados;
    }

    // --- STORED PROCEDURES ---

    private String ejecutarCargaEstudianteSP(String identificador, String nombres, String apellidos,
            String correo, String telefono) {

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("academico.sp_in_carga_estudiante");
        query.registerStoredProcedureParameter("p_identificador", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_nombres", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_apellidos", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_correo", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_telefono", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_mensaje", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_exito", Boolean.class, ParameterMode.OUT);

        query.setParameter("p_identificador", identificador);
        query.setParameter("p_nombres", nombres);
        query.setParameter("p_apellidos", apellidos);
        query.setParameter("p_correo", correo);
        query.setParameter("p_telefono", telefono);
        query.execute();

        String mensaje = (String) query.getOutputParameterValue("p_mensaje");
        Boolean exito = (Boolean) query.getOutputParameterValue("p_exito");
        return Boolean.TRUE.equals(exito) ? "OK" : "FALLÓ SP: " + mensaje;
    }

    private String ejecutarCargaDocenteSP(String cedula, String nom, String ape, String correo,
            String dir, String tel, Integer idMod, Integer idGen, Integer idPer,
            Integer idAsig, Integer idPar) {

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("academico.sp_in_carga_docente");
        query.registerStoredProcedureParameter("p_identificador", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_nombres", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_apellidos", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_correo", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_direccion", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_telefono", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_idmodalidad", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_idgenero", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_idperiodo", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_idasignatura", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_idparalelo", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_mensaje", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_exito", Boolean.class, ParameterMode.OUT);

        query.setParameter("p_identificador", cedula);
        query.setParameter("p_nombres", nom);
        query.setParameter("p_apellidos", ape);
        query.setParameter("p_correo", correo);
        query.setParameter("p_direccion", dir);
        query.setParameter("p_telefono", tel);
        query.setParameter("p_idmodalidad", idMod);
        query.setParameter("p_idgenero", idGen);
        query.setParameter("p_idperiodo", idPer);
        query.setParameter("p_idasignatura", idAsig);
        query.setParameter("p_idparalelo", idPar);
        query.execute();

        String mensaje = (String) query.getOutputParameterValue("p_mensaje");
        Boolean exito = (Boolean) query.getOutputParameterValue("p_exito");
        return Boolean.TRUE.equals(exito) ? "OK" : "FALLÓ SP: " + mensaje;
    }

    // --- CONVERSORES DTO ---

    private CoordinationDTO toDTO(Coordination entity) {
        CoordinationDTO dto = new CoordinationDTO();
        dto.setCoordinationId(entity.getCoordinationId());
        dto.setUserId(entity.getUserId() != null ? entity.getUserId().getUserId() : null);
        dto.setCareerId(entity.getCareerId() != null ? entity.getCareerId().getCareerId() : null);
        return dto;
    }

    private Coordination toEntity(CoordinationDTO dto) {
        Coordination entity = new Coordination();
        if (dto.getUserId() != null)
            entity.setUserId(userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found")));
        if (dto.getCareerId() != null)
            entity.setCareerId(careerRepository.findById(dto.getCareerId())
                    .orElseThrow(() -> new RuntimeException("Career not found")));
        return entity;
    }
}
