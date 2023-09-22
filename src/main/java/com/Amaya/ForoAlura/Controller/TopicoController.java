package com.Amaya.ForoAlura.Controller;

import com.Amaya.ForoAlura.Repositorios.RespuestaRepository;
import com.Amaya.ForoAlura.Repositorios.TopicoRepository;
import com.Amaya.ForoAlura.domain.Respuestas.DatosListadoRespuesta;
import com.Amaya.ForoAlura.domain.Respuestas.Respuesta;
import com.Amaya.ForoAlura.domain.Topico.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/topico")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Topico")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;


    @PostMapping
    @Transactional
    @Operation(summary = "Registra un topico")
    public ResponseEntity RegistrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                          UriComponentsBuilder uriComponentsBuilder) {

        String titulo = topicoRepository.SeleccionarPorTitulo(datosRegistroTopico.titulo());

        String mensaje = topicoRepository.SeleccionarPorMensaje(datosRegistroTopico.mensaje());

        //No permite el mismo titulo y el mismo mensaje de otro topico ya existente
        if (datosRegistroTopico.titulo().equals(titulo) && datosRegistroTopico.mensaje().equals(mensaje)) {

            return ResponseEntity.badRequest().body(" El titulo y el mensaje es el mismo que otro post, por favor modificalo");

        } else {

            Topico topico = topicoRepository.save(new Topico(datosRegistroTopico));

            DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),
                    topico.getMensaje(), topico.getFechaCreacion().toString(), topico.getEstatus(), topico.getAutor(),
                    topico.getCurso());

            URI uri = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(uri).body(datosRespuestaTopico);
        }
    }

    @GetMapping
    @Operation(summary = "Devuelve todos los topicos que existen")
    public List<Topico> ListarTopicos() {

        return topicoRepository.findAll();

    }

    @GetMapping("/{id}")
    @Operation(summary = "Devuelve un topico por el id y con las respuestas")
    public ResponseEntity<DatosListadoTopico> ListarTopicoPorId(@PathVariable long id) {

        Topico topico = topicoRepository.getReferenceById(id);

        ArrayList<Respuesta> respuestas = respuestaRepository.taerDatosPorIdTopico(topico.getId());

        var datosTopico = new DatosListadoTopico(topico.getTitulo(), topico.getMensaje(),
                topico.getFechaCreacion().toString(), topico.getEstatus(), topico.getAutor(), topico.getCurso(),
                respuestas);

        return ResponseEntity.ok(datosTopico);

    }

    @PutMapping
    @Transactional
    @Operation(summary = "Modifica un topico")
    public ResponseEntity ActualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {

        String titulo = topicoRepository.SeleccionarPorTitulo(datosActualizarTopico.titulo());

        String mensaje = topicoRepository.SeleccionarPorMensaje(datosActualizarTopico.mensaje());

        //No permite el mismo titulo y el mismo mensaje de otro topico ya existente
        if ( datosActualizarTopico.titulo() != null && datosActualizarTopico.mensaje() != null &&
                datosActualizarTopico.titulo().equals(titulo) && datosActualizarTopico.mensaje().equals(mensaje)) {

            return ResponseEntity.badRequest().body(" El titulo y el mensaje es el mismo que otro post, " +
                    "por favor modificalo");

        } else {

            Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());

            topico.actualizarDatos(datosActualizarTopico);

            DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),
                    topico.getMensaje(), topico.getFechaCreacion().toString(), topico.getEstatus(), topico.getAutor(),
                    topico.getCurso());

            return ResponseEntity.ok(datosRespuestaTopico);
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un topico")
    public ResponseEntity EliminarTopico(@PathVariable long id){

        topicoRepository.deleteById(id);

        return ResponseEntity.ok("El topico con el id: " + id + " fue eliminado");

    }


    @PutMapping("/deshabilitar/{id}")
    @Transactional
    @Operation(summary = "Deshabilitar un topico")
    public ResponseEntity deshabilitarTopico(@PathVariable long id){

        Topico topico = topicoRepository.getReferenceById(id);

        topico.Deshabilitar();

        return ResponseEntity.ok("El topico con el id: " + id + " fue deshabilitado");

    }


}

