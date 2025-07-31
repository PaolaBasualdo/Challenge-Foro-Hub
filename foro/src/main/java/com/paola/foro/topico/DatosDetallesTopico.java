package com.paola.foro.topico;

import java.time.LocalDateTime;

public record DatosDetallesTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean activo,
        String nombreAutor,
        Curso curso
) {}
