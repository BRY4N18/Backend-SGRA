package com.CLMTZ.Backend.dto.academic;

import lombok.Data;

@Data
public class StudentLoadDTO {
    private String cedula;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String direccion;
    private String genero; // "M" o "F"

    // Datos académicos (Texto del Excel)
    private String carreraTexto;
    private String modalidadTexto;
    private String periodoTexto;

}
