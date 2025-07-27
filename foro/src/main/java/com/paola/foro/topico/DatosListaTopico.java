package com.paola.foro.topico;

import com.paola.foro.topico.Topico;
import com.paola.foro.topico.Curso;

import java.time.LocalDateTime;

public record DatosListaTopico(
        Long id,
        String titulo,
        String mensaje,
        String nombreAlumno,
        Curso curso,
        LocalDateTime fechaCreacion
) {
    public DatosListaTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAlumno().getNombre(),
                topico.getCurso(),
                topico.getFechaCreacion()
        );
    }
}
