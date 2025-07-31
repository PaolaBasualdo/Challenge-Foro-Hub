package com.paola.foro.topico;

import com.paola.foro.alumno.Alumno;
import com.paola.foro.topico.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroTopico(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull Long idAlumno,
        @NotNull Curso curso
        //@NotNull LocalDateTime fechaCreacion
) {}