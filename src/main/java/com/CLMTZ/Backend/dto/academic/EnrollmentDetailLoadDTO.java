package com.CLMTZ.Backend.dto.academic;

import lombok.Data;

@Data
public class EnrollmentDetailLoadDTO {
    private String cedulaEstudiante;  // Ej: "1205487963"
    private String periodo;           // Ej: "2026-CI"
    private String nombreAsignatura;  // Ej: "Matemáticas"
    private String paralelo;          // Ej: "A"
}
