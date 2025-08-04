package com.paola.foro.controller;

import com.paola.foro.alumno.AlumnoRepository;
import com.paola.foro.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> registrar(@RequestBody @Valid DatosRegistroTopico datos) {
        if (repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un tópico con el mismo título y mensaje.");
        }

        var alumnoOptional = alumnoRepository.findById(datos.idAlumno());
        if (alumnoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno no encontrado");
        }

        Topico topico = new Topico(datos, alumnoOptional.get());
        repository.save(topico);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listar(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {
        var page = repository.findByActivoTrue(paginacion).map(DatosListaTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerDetalle(@PathVariable Long id) {
        var topicoOptional = repository.findById(id);
        if (topicoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado");
        }

        var topico = topicoOptional.get();

        if (!topico.getActivo()) {
            return ResponseEntity.status(HttpStatus.GONE).body("El tópico está inactivo");
        }

        var datos = new DatosDetallesTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getActivo(),
                topico.getAlumno().getNombre(),
                topico.getCurso()
        );

        return ResponseEntity.ok(datos);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datos) {
        var topicoOptional = repository.findById(id);
        if (topicoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado");
        }

        var topico = topicoOptional.get();
        topico.actualizarInformacion(datos);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        var topicoOptional = repository.findById(id);
        if (topicoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado");
        }

        var topico = topicoOptional.get();
        topico.eliminar();

        return ResponseEntity.noContent().build();
    }
}

