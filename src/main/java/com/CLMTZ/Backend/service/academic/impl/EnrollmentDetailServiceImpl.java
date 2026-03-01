package com.CLMTZ.Backend.service.academic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.CLMTZ.Backend.dto.academic.EnrollmentDetailDTO;
import com.CLMTZ.Backend.dto.academic.EnrollmentDetailLoadDTO;
import com.CLMTZ.Backend.model.academic.EnrollmentDetail;
import com.CLMTZ.Backend.repository.academic.*;
import com.CLMTZ.Backend.service.academic.IEnrollmentDetailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentDetailServiceImpl implements IEnrollmentDetailService {

    private final IEnrollmentDetailRepository repository;
    private final IRegistrationsRepository registrationsRepository;
    private final ISubjectRepository subjectRepository;
    private final IParallelRepository parallelRepository;
    private final IDataLoadRepository dataLoadRepository;

    @Override
    public List<EnrollmentDetailDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public EnrollmentDetailDTO findById(Integer id) {
        return repository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("EnrollmentDetail not found with id: " + id));
    }

    @Override
    public EnrollmentDetailDTO save(EnrollmentDetailDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    @Override
    public EnrollmentDetailDTO update(Integer id, EnrollmentDetailDTO dto) {
        EnrollmentDetail entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("EnrollmentDetail not found with id: " + id));
        entity.setActive(dto.getActive());
        if (dto.getRegistrationId() != null) entity.setRegistrationId(registrationsRepository.findById(dto.getRegistrationId()).orElseThrow(() -> new RuntimeException("Registration not found")));
        if (dto.getSubjectId() != null) entity.setSubjectId(subjectRepository.findById(dto.getSubjectId()).orElseThrow(() -> new RuntimeException("Subject not found")));
        if (dto.getParallelId() != null) entity.setParallelId(parallelRepository.findById(dto.getParallelId()).orElseThrow(() -> new RuntimeException("Parallel not found")));
        return toDTO(repository.save(entity));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    private EnrollmentDetailDTO toDTO(EnrollmentDetail entity) {
        EnrollmentDetailDTO dto = new EnrollmentDetailDTO();
        dto.setEnrollmentDetailId(entity.getEnrollmentDetailId());
        dto.setActive(entity.getActive());
        dto.setRegistrationId(entity.getRegistrationId() != null ? entity.getRegistrationId().getRegistrationId() : null);
        dto.setSubjectId(entity.getSubjectId() != null ? entity.getSubjectId().getIdSubject() : null);
        dto.setParallelId(entity.getParallelId() != null ? entity.getParallelId().getParallelId() : null);
        return dto;
    }

    private EnrollmentDetail toEntity(EnrollmentDetailDTO dto) {
        EnrollmentDetail entity = new EnrollmentDetail();
        entity.setActive(dto.getActive() != null ? dto.getActive() : true);
        if (dto.getRegistrationId() != null) entity.setRegistrationId(registrationsRepository.findById(dto.getRegistrationId()).orElseThrow(() -> new RuntimeException("Registration not found")));
        if (dto.getSubjectId() != null) entity.setSubjectId(subjectRepository.findById(dto.getSubjectId()).orElseThrow(() -> new RuntimeException("Subject not found")));
        if (dto.getParallelId() != null) entity.setParallelId(parallelRepository.findById(dto.getParallelId()).orElseThrow(() -> new RuntimeException("Parallel not found")));
        return entity;
    }

    @Override
    public List<String> uploadEnrollmentDetails(List<EnrollmentDetailLoadDTO> registrationDTOs) {
        List<String> report = new ArrayList<>();

        if (registrationDTOs == null || registrationDTOs.isEmpty()) {
            report.add("ADVERTENCIA: No se encontraron registros de matrícula en el archivo. Verifique el formato del Excel.");
            return report;
        }

        // Periodo activo (gestionado por el admin, no viene del Excel)
        Integer idPeriodo = dataLoadRepository.obtenerIdPeriodoActivo();
        if (idPeriodo == null) {
            report.add("ERROR GENERAL: No hay un periodo activo configurado. Contacte al administrador.");
            return report;
        }

        for (EnrollmentDetailLoadDTO dto : registrationDTOs) {
            String ref = "Estudiante '" + dto.getCedulaEstudiante() + "' - Asig. '" + dto.getNombreAsignatura() + "'";
            try {
                // 1. Verificar que el estudiante tiene matrícula en el periodo activo
                //    (Requiere que el paso 1 - upload-students - ya se haya ejecutado)
                Integer idMatricula = dataLoadRepository.obtenerIdMatricula(dto.getCedulaEstudiante(), idPeriodo);
                if (idMatricula == null) {
                    report.add(ref + ": ERROR (Estudiante sin matrícula en el periodo activo. Cargue primero Estudiantes)");
                    continue;
                }

                // 2. Obtener ID de carrera a partir del texto del encabezado del Excel
                Map<String, Object> ids = dataLoadRepository.obtenerIdsPorCarrera(dto.getCarreraTexto());
                if (ids == null || ids.get("id_carrera_encontrado") == null) {
                    report.add(ref + ": ERROR (Carrera no encontrada en BD: '" + dto.getCarreraTexto() + "')");
                    continue;
                }
                Integer idCarrera = ((Number) ids.get("id_carrera_encontrado")).intValue();

                // 3. Obtener ID de asignatura por nombre y carrera
                Integer idAsignatura = dataLoadRepository.obtenerIdAsignatura(dto.getNombreAsignatura(), idCarrera);
                if (idAsignatura == null) {
                    report.add(ref + ": ERROR (Asignatura '" + dto.getNombreAsignatura() + "' no encontrada en esa carrera)");
                    continue;
                }

                // 4. Crear el detalle de matrícula con las relaciones correctas
                //    paralelo: no viene en Matricula.xlsx → null
                EnrollmentDetail detail = new EnrollmentDetail();
                detail.setRegistrationId(registrationsRepository.getReferenceById(idMatricula));
                detail.setSubjectId(subjectRepository.getReferenceById(idAsignatura));
                detail.setActive(true);
                repository.save(detail);

                report.add(ref + ": OK");

            } catch (Exception e) {
                report.add(ref + ": ERROR (" + e.getMessage() + ")");
            }
        }

        long exitosos = report.stream().filter(r -> r.endsWith(": OK")).count();
        long errores = report.stream().filter(r -> r.contains(": ERROR")).count();
        report.add(0, "RESUMEN: " + registrationDTOs.size() + " registros → " + exitosos + " exitosos, " + errores + " con errores.");

        return report;
    }
}
