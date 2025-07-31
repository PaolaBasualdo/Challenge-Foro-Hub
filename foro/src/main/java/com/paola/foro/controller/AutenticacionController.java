package com.paola.foro.controller;

import com.paola.foro.alumno.Alumno;
import com.paola.foro.alumno.AlumnoRepository;
import com.paola.foro.alumno.DatosAutenticacion;
import com.paola.foro.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @PostMapping
    public ResponseEntity<String> autenticar(@RequestBody @Valid DatosAutenticacion datos) {
        // Crea el token con el email y password
        var authToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.password());

        // Intenta autenticar al usuario
        var autenticacion = manager.authenticate(authToken);

        // Si pasa la autenticación, obtenemos el usuario (Alumno)
        var alumno = (Alumno) autenticacion.getPrincipal();

        // Generamos el JWT
        var jwt = tokenService.generarToken(alumno);

        // Devolvemos el token
        return ResponseEntity.ok(jwt);
    }
}
