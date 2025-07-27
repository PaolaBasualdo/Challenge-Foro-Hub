package com.paola.foro.alumno;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosRegistroAlumno(
        @NotBlank String nombre,
        @NotBlank @Email String email,
        @NotBlank String password
) {}
