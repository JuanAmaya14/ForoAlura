package com.Amaya.ForoAlura.Controller;

import com.Amaya.ForoAlura.Repositorios.TopicoRepository;
import com.Amaya.ForoAlura.domain.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;


    @PostMapping
    @Transactional
    public ResponseEntity RegistrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                          UriComponentsBuilder uriComponentsBuilder) {

        String titulo = topicoRepository.SeleccionarPorTitulo(datosRegistroTopico.titulo());

        String mensaje = topicoRepository.SeleccionarPorMensaje(datosRegistroTopico.mensaje());

        if (datosRegistroTopico.titulo().equals(titulo) && datosRegistroTopico.mensaje().equals(mensaje)) {

            return ResponseEntity.badRequest().body(" El titulo y el mensaje es el mismo que otro post, por favor modificalo");

        } else {

            Topico topico = topicoRepository.save(new Topico(datosRegistroTopico));

            DatosListadoTopico datosListadoTopico = new DatosListadoTopico(topico.getTitulo(),
                    topico.getMensaje(), topico.getFechaCreacion().toString(), topico.getEstatus(), topico.getAutor(),
                    topico.getCurso());

            URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(uri).body(datosListadoTopico);
        }
    }

    @GetMapping
    public List<Topico> ListarTopicos() {

        return topicoRepository.findAll();

    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoTopico> ListarTopicoPorId(@PathVariable long id) {

        Topico topico = topicoRepository.getReferenceById(id);

        var DatosTopico = new DatosListadoTopico(topico.getTitulo(), topico.getMensaje(),
                topico.getFechaCreacion().toString(), topico.getEstatus(), topico.getAutor(), topico.getCurso());

        return ResponseEntity.ok(DatosTopico);

    }

    @PutMapping
    @Transactional
    public ResponseEntity ActualizarTopico(@RequestBody @Valid DatosActualizaTopico datosActualizaTopico) {

        String titulo = topicoRepository.SeleccionarPorTitulo(datosActualizaTopico.titulo());

        String mensaje = topicoRepository.SeleccionarPorMensaje(datosActualizaTopico.mensaje());

        if ( datosActualizaTopico.titulo() != null && datosActualizaTopico.mensaje() != null &&
                datosActualizaTopico.titulo().equals(titulo) && datosActualizaTopico.mensaje().equals(mensaje)) {

            return ResponseEntity.badRequest().body(" El titulo y el mensaje es el mismo que otro post, " +
                    "por favor modificalo");

        } else {

            Topico topico = topicoRepository.getReferenceById(datosActualizaTopico.id());

            topico.actualizarDatos(datosActualizaTopico);

            DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),
                    topico.getMensaje(), topico.getFechaCreacion().toString(), topico.getEstatus(), topico.getAutor(),
                    topico.getCurso());

            return ResponseEntity.ok(datosRespuestaTopico);
        }
    }
}

