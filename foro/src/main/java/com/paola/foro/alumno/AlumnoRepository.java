package com.paola.foro.alumno;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    Optional<Alumno> findByEmail(String email);
    // Si más adelante se necesita buscar por email, por ejemplo:
    // Optional<Alumno> findByEmail(String email);
}
