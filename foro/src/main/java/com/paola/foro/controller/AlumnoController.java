package com.paola.foro.controller;

import com.paola.foro.alumno.Alumno;
import com.paola.foro.alumno.AlumnoRepository;
import com.paola.foro.alumno.DatosRegistroAlumno;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public void registrar(@RequestBody @Valid DatosRegistroAlumno datos) {
        String passwordEncriptada = passwordEncoder.encode(datos.password());
        Alumno alumno = new Alumno(datos.nombre(), datos.email(), passwordEncriptada);
        repository.save(alumno);
    }
}
