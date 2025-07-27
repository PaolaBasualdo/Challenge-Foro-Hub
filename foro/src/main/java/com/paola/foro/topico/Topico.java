package com.paola.foro.topico;

import com.paola.foro.alumno.Alumno;
import com.paola.foro.topico.DatosActualizarTopico;
import com.paola.foro.topico.DatosRegistroTopico;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean activo;

    private String titulo;
    private String mensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @Enumerated(EnumType.STRING)
    private Curso curso;

    private LocalDateTime fechaCreacion;

    public Topico(DatosRegistroTopico datos, Alumno alumno) {
        this.activo = true;
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.alumno = alumno;
        this.curso = datos.curso();
        this.fechaCreacion = datos.fechaCreacion();
    }

    public void actualizarInformacion(@Valid DatosActualizarTopico datos) {
        if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }
        if (datos.curso() != null) {
            this.curso = datos.curso();
        }
    }

    public void eliminar() {
        this.activo = false;
    }
}
