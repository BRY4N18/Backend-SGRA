package com.CLMTZ.Backend.util;

import com.CLMTZ.Backend.dto.academic.CareerLoadDTO;
import com.CLMTZ.Backend.dto.academic.ClassScheduleLoadDTO;
import com.CLMTZ.Backend.dto.academic.EnrollmentDetailLoadDTO;
import com.CLMTZ.Backend.dto.academic.StudentLoadDTO;
import com.CLMTZ.Backend.dto.academic.SubjectLoadDTO;
import com.CLMTZ.Backend.dto.academic.TeachingDTO;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelHelper {

    private static final DataFormatter formatter = new DataFormatter();
    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String TYPE_XLS = "application/vnd.ms-excel";

    // Columnas de resumen en Matricula.xlsx (no son asignaturas)
    private static final java.util.Set<String> COLUMNAS_RESUMEN = java.util.Set.of(
            "APROBADAS", "REPROBADAS", "MATRICULADAS", "PENDIENTES", "TOTALES");

    public static boolean hasExcelFormat(MultipartFile file) {
        if (file == null || file.isEmpty()) return false;

        String filename = file.getOriginalFilename();
        if (filename != null && (filename.toLowerCase().endsWith(".xlsx") || filename.toLowerCase().endsWith(".xls"))) {
            return true;
        }

        String contentType = file.getContentType();
        if (contentType != null) {
            return TYPE.equals(contentType) || TYPE_XLS.equals(contentType)
                    || contentType.contains("spreadsheet") || contentType.contains("excel");
        }
        return false;
    }

    // =====================================================================
    // ESTUDIANTES.xls
    // Formato:
    //   Fila 0: Encabezado institución ("UNIVERSIDAD TÉCNICA ESTATAL DE QUEVEDO")
    //   Fila 1: Vacía
    //   Fila 2: Cabeceras (ESTUDIANTE | IDENTIFICACIÓN | EMAIL INSTITUCIONAL | TELÉFONO | CARRERA | MODALIDAD | GENERO)
    //   Fila 3+: Datos
    //     Col 0: Nombre completo (NOMBRES APELLIDOS) ej. "DAMARYS RAELITH AGUILAR LECHON"
    //     Col 1: Cédula
    //     Col 2: Email institucional
    //     Col 3: Teléfono
    //     Col 4: Carrera
    //     Col 5: Modalidad
    //     Col 6: Género
    // =====================================================================
    public static List<StudentLoadDTO> excelToStudents(InputStream is, String carreraTexto, String modalidadTexto) {
        try (Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<StudentLoadDTO> estudiantes = new ArrayList<>();

            // Datos empiezan en fila 3 (índice 3), filas 0-2 son encabezados
            for (int i = 3; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) continue;

                String nombreCompleto = getCellValue(row, 0).trim();
                String cedula = getCellValue(row, 1).trim();
                String correo = getCellValue(row, 2).trim();
                String telefono = getCellValue(row, 3).trim();
                String carreraFila = getCellValue(row, 4).trim();
                String modalidadFila = getCellValue(row, 5).trim();

                if (cedula.isEmpty()) continue;

                // El nombre viene en formato NOMBRES APELLIDOS: ej. "DAMARYS RAELITH AGUILAR LECHON"
                String[] partes = splitNombreApellido(nombreCompleto, false);

                StudentLoadDTO estudiante = new StudentLoadDTO();
                estudiante.setCedula(cedula);
                estudiante.setNombres(partes[0]);
                estudiante.setApellidos(partes[1]);
                estudiante.setCorreo(correo);
                estudiante.setTelefono(telefono);
                // Carrera y modalidad se leen del propio archivo; los parámetros del endpoint son fallback
                estudiante.setCarreraTexto(!carreraFila.isEmpty() ? carreraFila : carreraTexto);
                estudiante.setModalidadTexto(!modalidadFila.isEmpty() ? modalidadFila : modalidadTexto);

                estudiantes.add(estudiante);
            }
            return estudiantes;
        } catch (IOException e) {
            throw new RuntimeException("Error al parsear el archivo Excel de Estudiantes: " + e.getMessage());
        }
    }

    // =====================================================================
    // DOCENTE.xls
    // Formato:
    //   Fila 0: Vacía
    //   Fila 1: Cabeceras (COORDINACIÓN | CARRERA | NIVEL | MATERIA | PARALELO | PROFESOR)
    //   Fila 2+: Datos
    //     Col 0: COORDINACIÓN (facultad/área)
    //     Col 1: CARRERA
    //     Col 2: NIVEL (ej. "1ER NIVEL")
    //     Col 3: MATERIA (asignatura)
    //     Col 4: PARALELO (ej. "B")
    //     Col 5: PROFESOR (APELLIDOS NOMBRES) ej. "BOSQUEZ MESTANZA ANGELITA LEONOR"
    // =====================================================================
    public static List<TeachingDTO> excelToTeaching(InputStream is) {
        try (Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<TeachingDTO> docentes = new ArrayList<>();

            // Datos empiezan en fila 2 (índice 2), filas 0-1 son encabezados
            for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) continue;

                String coordinacion = getCellValue(row, 0).trim();
                String carrera = getCellValue(row, 1).trim();
                String nivel = getCellValue(row, 2).trim();
                String materia = getCellValue(row, 3).trim();
                String paralelo = getCellValue(row, 4).trim();
                String nombreProfesor = getCellValue(row, 5).trim();

                if (nombreProfesor.isEmpty() && materia.isEmpty()) continue;

                // Nombre del profesor viene en APELLIDOS NOMBRES: ej. "BOSQUEZ MESTANZA ANGELITA LEONOR"
                String[] partes = splitNombreApellido(nombreProfesor, true);

                TeachingDTO docente = new TeachingDTO();
                docente.setCoordinacionTexto(coordinacion);
                docente.setCarreraTexto(carrera);
                docente.setNivelTexto(nivel);
                docente.setAsignaturaTexto(materia);
                docente.setParaleloTexto(paralelo);
                docente.setNombreCompleto(nombreProfesor);
                docente.setApellidos(partes[0]); // En Docente.xls: apellidos van primero
                docente.setNombres(partes[1]);   // nombres van segundo

                docentes.add(docente);
            }
            return docentes;
        } catch (IOException e) {
            throw new RuntimeException("Error al parsear el archivo Excel de Docentes: " + e.getMessage());
        }
    }

    // =====================================================================
    // MATRICULA.xlsx
    // Formato complejo (reporte de cumplimiento de malla):
    //   Fila 0: Nombre institución
    //   Fila 1: Nombre periodo (ej. "REGULAR 2025-2026 SPA")
    //   Fila 2: Nombre facultad
    //   Fila 3: Nombre carrera (ej. "SOFTWARE (REDISEÑO) (MARZO 2018 VIGENTE)...")
    //   Fila 4: Título del reporte
    //   Fila 5: Vacía
    //   Fila 6: Nombres de asignaturas en columnas 9+
    //   Fila 7: Cabeceras (NIVEL_MAT, NIVEL_EST, NIVEL_COM, IDENTIFICACIÓN, APELLIDOS, NOMBRES, SEXO, EMAIL, CELULAR, ...)
    //   Fila 8+: Datos de estudiantes
    //     Col 3: Cédula (IDENTIFICACIÓN)
    //     Col 9+: Estado de matrícula por asignatura (M = Matriculado)
    //
    // El periodo NO viene en el archivo; se obtiene del periodo activo en BD.
    // Genera un EnrollmentDetailLoadDTO por cada asignatura donde el estudiante tiene "M".
    // =====================================================================
    public static List<EnrollmentDetailLoadDTO> excelToEnrollmentDetails(InputStream is) {
        try (Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<EnrollmentDetailLoadDTO> detalles = new ArrayList<>();

            // Leer carrera del encabezado (fila 3, col 0)
            Row filaCarrera = sheet.getRow(3);
            String carreraTexto = (filaCarrera != null) ? getCellValue(filaCarrera, 0).trim() : "";

            // Leer nombres de asignaturas de fila 6 (índice 6), columnas 9 en adelante
            Row filaAsignaturas = sheet.getRow(6);
            if (filaAsignaturas == null) {
                throw new RuntimeException("El archivo no tiene el formato esperado (falta fila de asignaturas).");
            }

            // Mapa: columna -> nombre asignatura
            Map<Integer, String> asignaturasPorColumna = new LinkedHashMap<>();
            for (int col = 9; col <= filaAsignaturas.getLastCellNum(); col++) {
                String nombreAsig = getCellValue(filaAsignaturas, col).trim();
                if (nombreAsig.isEmpty() || COLUMNAS_RESUMEN.contains(nombreAsig.toUpperCase())) continue;
                asignaturasPorColumna.put(col, nombreAsig);
            }

            // Datos de estudiantes: fila 8 (índice 8) en adelante
            for (int i = 8; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) continue;

                String cedula = getCellValue(row, 3).trim();
                if (cedula.isEmpty()) continue;

                // Para cada asignatura, si el estado es "M" crear un detalle de matrícula
                for (Map.Entry<Integer, String> entry : asignaturasPorColumna.entrySet()) {
                    int col = entry.getKey();
                    String estado = getCellValue(row, col).trim().toUpperCase();

                    if ("M".equals(estado)) {
                        EnrollmentDetailLoadDTO detalle = new EnrollmentDetailLoadDTO();
                        detalle.setCedulaEstudiante(cedula);
                        detalle.setNombreAsignatura(entry.getValue());
                        detalle.setCarreraTexto(carreraTexto);
                        detalles.add(detalle);
                    }
                }
            }
            return detalles;
        } catch (IOException e) {
            throw new RuntimeException("Error al parsear el archivo Excel de Matrícula: " + e.getMessage());
        }
    }

    // =====================================================================
    // CARRERAS (formato propio del sistema, sin cambios en estructura)
    // =====================================================================
    public static List<CareerLoadDTO> excelToCareers(InputStream is) {
        try (Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<CareerLoadDTO> careerList = new ArrayList<>();

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) continue;

                CareerLoadDTO career = new CareerLoadDTO();
                career.setNombreArea(getCellValue(row, 0));
                career.setAbrevArea(getCellValue(row, 1));
                career.setNombreModalidad(getCellValue(row, 2));
                career.setNombreCarrera(getCellValue(row, 3));
                String semestresStr = getCellValue(row, 4).replace(".0", "");
                career.setSemestres(semestresStr.isEmpty() ? 0 : Short.parseShort(semestresStr));
                careerList.add(career);
            }
            return careerList;
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear el archivo Excel de Carreras: " + e.getMessage());
        }
    }

    // =====================================================================
    // ASIGNATURAS (formato propio del sistema, sin cambios en estructura)
    // =====================================================================
    public static List<SubjectLoadDTO> excelToSubjects(InputStream is) {
        try (Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<SubjectLoadDTO> subjectList = new ArrayList<>();

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) continue;

                SubjectLoadDTO subject = new SubjectLoadDTO();
                subject.setNombreCarrera(getCellValue(row, 0));
                subject.setNombreAsignatura(getCellValue(row, 1));
                String semestreStr = getCellValue(row, 2).replace(".0", "");
                subject.setSemestre(semestreStr.isEmpty() ? 0 : Short.parseShort(semestreStr));
                subjectList.add(subject);
            }
            return subjectList;
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear el archivo Excel de Asignaturas: " + e.getMessage());
        }
    }

    // =====================================================================
    // HORARIOS DE CLASES (formato propio del sistema, sin cambios en estructura)
    // =====================================================================
    public static List<ClassScheduleLoadDTO> excelToClassSchedules(InputStream is) {
        try (Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<ClassScheduleLoadDTO> scheduleList = new ArrayList<>();

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) continue;

                ClassScheduleLoadDTO schedule = new ClassScheduleLoadDTO();
                schedule.setCedulaDocente(getCellValue(row, 0));
                schedule.setNombreAsignatura(getCellValue(row, 1));
                schedule.setNombreParalelo(getCellValue(row, 2));
                schedule.setNombrePeriodo(getCellValue(row, 3));
                String diaStr = getCellValue(row, 4).replace(".0", "");
                schedule.setDiaSemana(diaStr.isEmpty() ? 0 : Integer.parseInt(diaStr));
                schedule.setHoraInicio(parseExcelTime(getCellValue(row, 5)));
                schedule.setHoraFin(parseExcelTime(getCellValue(row, 6)));
                scheduleList.add(schedule);
            }
            return scheduleList;
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear el archivo Excel de Horarios: " + e.getMessage());
        }
    }

    // =====================================================================
    // UTILIDADES PRIVADAS
    // =====================================================================

    /**
     * Divide un nombre completo en [nombres, apellidos] o [apellidos, nombres].
     *
     * @param nombreCompleto el texto a dividir
     * @param apellidosPrimero true si el formato es "APELLIDOS NOMBRES" (Docente.xls),
     *                         false si es "NOMBRES APELLIDOS" (Estudiantes.xls)
     * @return arreglo de 2 elementos: [0]=primera parte, [1]=segunda parte
     */
    private static String[] splitNombreApellido(String nombreCompleto, boolean apellidosPrimero) {
        if (nombreCompleto == null || nombreCompleto.isEmpty()) {
            return new String[]{"", ""};
        }

        String[] partes = nombreCompleto.trim().split("\\s+");
        int total = partes.length;

        if (total == 1) {
            return apellidosPrimero ? new String[]{partes[0], ""} : new String[]{"", partes[0]};
        }
        if (total == 2) {
            return new String[]{partes[0], partes[1]};
        }

        // Para 3+ palabras: convención UTEQ es 2 apellidos + 2 nombres (o 2+1, 1+2, etc.)
        // Con 4 palabras: split al medio (2+2)
        // Con 3 palabras: primera parte = 2 palabras, segunda = 1
        // Con 5+: primera parte = 2 palabras, el resto va a segunda
        int splitIdx = (total >= 4) ? 2 : (total == 3 ? 2 : 1);

        StringBuilder primera = new StringBuilder();
        StringBuilder segunda = new StringBuilder();
        for (int i = 0; i < total; i++) {
            if (i < splitIdx) {
                if (primera.length() > 0) primera.append(" ");
                primera.append(partes[i]);
            } else {
                if (segunda.length() > 0) segunda.append(" ");
                segunda.append(partes[i]);
            }
        }

        // apellidosPrimero=true  → primera=apellidos, segunda=nombres → return [apellidos, nombres]
        // apellidosPrimero=false → primera=nombres, segunda=apellidos  → return [nombres, apellidos]
        return new String[]{primera.toString(), segunda.toString()};
    }

    private static String getCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null || cell.getCellType() == CellType.BLANK) return "";
        return formatter.formatCellValue(cell).trim();
    }

    private static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                if (!getCellValue(row, c).isEmpty()) return false;
            }
        }
        return true;
    }

    private static LocalTime parseExcelTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) return null;
        timeStr = timeStr.trim();
        if (timeStr.matches("^\\d:\\d{2}.*")) timeStr = "0" + timeStr;
        if (timeStr.length() > 5) timeStr = timeStr.substring(0, 5);
        return LocalTime.parse(timeStr);
    }
}
