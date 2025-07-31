package com.paola.foro.controller;



import com.paola.foro.alumno.AlumnoRepository;
import com.paola.foro.topico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;



import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private AlumnoRepository alumnoRepository;


    @PostMapping
    @Transactional
    public void registrar(@RequestBody @Valid DatosRegistroTopico datos) {
        boolean existe = repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if (existe) {
            throw new IllegalArgumentException("Ya existe un tópico con el mismo título y mensaje.");
        }

        var alumnoOptional = alumnoRepository.findById(datos.idAlumno());
        if (alumnoOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alumno no encontrado");
        }

        Topico topico = new Topico(datos, alumnoOptional.get());
        repository.save(topico);
    }


    @GetMapping
    public Page<DatosListaTopico> listar(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {
        return repository.findByActivoTrue(paginacion).map(DatosListaTopico::new);
    }

    @GetMapping("/{id}")
    public DatosDetallesTopico obtenerDetalle(@PathVariable Long id) {
        var topicoOptional = repository.findById(id);
        if (topicoOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado");
        }

        var topico = topicoOptional.get();

        if (!topico.getActivo()) {
            throw new ResponseStatusException(HttpStatus.GONE, "El tópico está inactivo");
        }

        return new DatosDetallesTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getActivo(),
                topico.getAlumno().getNombre(),
                topico.getCurso()
        );
    }



    @PutMapping("/{id}")
    @Transactional
    public void actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datos) {
        var topicoOptional = repository.findById(id);
        if (topicoOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado");
        }

        var topico = topicoOptional.get();
        topico.actualizarInformacion(datos);
    }


    @Transactional
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        var topico = repository.getReferenceById(id);
        topico.eliminar();
    }
}
