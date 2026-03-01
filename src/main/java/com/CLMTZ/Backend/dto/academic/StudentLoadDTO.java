package com.CLMTZ.Backend.dto.academic;

import lombok.Data;

@Data
public class StudentLoadDTO {
    private String cedula;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String genero; // "MUJER" o "HOMBRE" -> se convierte a ID en el servicio

    // Datos académicos (Texto del Excel o parámetro del endpoint)
    private String carreraTexto;
    private String modalidadTexto;
}
