package com.paola.foro.alumno;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    public Alumno(DatosRegistroAlumno datos) {
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.password = datos.password(); // Más adelante la vas a encriptar con BCrypt
    }


}
