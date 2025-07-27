package com.paola.foro.alumno;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    // Si más adelante se necesita buscar por email, por ejemplo:
    // Optional<Alumno> findByEmail(String email);
}
