package com.Amaya.ForoAlura.Controller;

import com.Amaya.ForoAlura.Repositorios.*;
import com.Amaya.ForoAlura.domain.Respuestas.DatosRespuesta.*;
import com.Amaya.ForoAlura.domain.Respuestas.Respuesta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/respuesta")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Respuesta a topico")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation(summary = "Devuelve todas las respuestas que existen")
    public List<Respuesta> listarRespuestas() {

        return respuestaRepository.findAll();

    }

    @GetMapping("/{id}")
    @Operation(summary = "Devuelve una respeusta por el id")
    public ResponseEntity<DatosListadoRespuesta> listarRespuestas(@PathVariable long id) {

        Respuesta respuesta = respuestaRepository.getReferenceById(id);

        String titulo = respuestaRepository.tituloTopicoPorId(respuesta.getIdTopico());

        String autor = respuestaRepository.autorRespuestaPorId(respuesta.getIdAutor());

        DatosListadoRespuesta datosListadoRespuesta = new DatosListadoRespuesta(respuesta.getMensajeRespuesta(),
                respuesta.getFechaRespuesta().toString(), titulo, autor);

        return ResponseEntity.ok(datosListadoRespuesta);

    }

    @PostMapping
    @Transactional
    @Operation(summary = "Registra una respuesta")
    public ResponseEntity registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta,
                                             UriComponentsBuilder uriComponentsBuilder) {

        Boolean topico = topicoRepository.estaDeshabilitado(datosRegistroRespuesta.idTopico());

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        long idUsuario = usuarioRepository.getIdUsuarioByCorreo(userDetail.getUsername());

        //Si el topico esta dehabilitado este no resibira las respuestas
        if (topico) {

            Respuesta respuesta = respuestaRepository.save(new Respuesta(datosRegistroRespuesta, idUsuario));

            DatosRespuestaRespuesta datosRespuestaRespuesta = new DatosRespuestaRespuesta(respuesta.getId(),
                    respuesta.getMensajeRespuesta(), respuesta.getFechaRespuesta().toString(), respuesta.getIdTopico(),
                    respuesta.getIdAutor());

            URI uri = uriComponentsBuilder.path("/respuesta/{id}").buildAndExpand(respuesta.getId()).toUri();
            return ResponseEntity.created(uri).body(datosRespuestaRespuesta);

        } else {

            return ResponseEntity.badRequest().body("El topico esta deshabilitado");

        }


    }


    @PutMapping
    @Transactional
    @Operation(summary = "Modifica una respuesta por el id")
    public ResponseEntity modificarRepsuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta) {

        Respuesta respuesta = respuestaRepository.getReferenceById(datosActualizarRespuesta.id());

        respuesta.modificarRespuesta(datosActualizarRespuesta);

        DatosRespuestaRespuesta datosRespuestaRespuesta = new DatosRespuestaRespuesta(respuesta.getId(),
                respuesta.getMensajeRespuesta(), respuesta.getFechaRespuesta().toString(), respuesta.getIdTopico(),
                respuesta.getIdAutor());


        return ResponseEntity.ok(datosRespuestaRespuesta);

    }

    @DeleteMapping("/eliminar/{id}")
    @Transactional
    @Operation(summary = "Elimina una respuesta")
    public ResponseEntity eliminarRespuesta(@PathVariable long id) {

        respuestaRepository.deleteById(id);

        return ResponseEntity.ok("La respuesta con el id " + id + " fue eliminado con exito");

    }

}
