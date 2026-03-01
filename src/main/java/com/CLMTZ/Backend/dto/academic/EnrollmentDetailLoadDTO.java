package com.CLMTZ.Backend.dto.academic;

import lombok.Data;

@Data
public class EnrollmentDetailLoadDTO {
    private String cedulaEstudiante;  // Col 3: IDENTIFICACIÓN
    private String nombreAsignatura;  // Del encabezado de columna (fila 6 del Excel)
    private String carreraTexto;      // Del encabezado del archivo (fila 3 del Excel)
    // Periodo: se obtiene del periodo activo en BD (no viene en el Excel)
}
