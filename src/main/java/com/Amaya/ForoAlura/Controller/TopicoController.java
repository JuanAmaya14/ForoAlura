package com.Amaya.ForoAlura.Controller;

import com.Amaya.ForoAlura.Repositorios.*;
import com.Amaya.ForoAlura.domain.Respuestas.Respuesta;
import com.Amaya.ForoAlura.domain.Topico.DatosTopico.*;
import com.Amaya.ForoAlura.domain.Topico.Topico;
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

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping
    @Transactional
    @Operation(summary = "Registra un topico")
    public ResponseEntity RegistrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                          UriComponentsBuilder uriComponentsBuilder) {

        String idTopico = topicoRepository.SeleccionarIdPorTituloYMensaje(datosRegistroTopico.titulo(),
                datosRegistroTopico.mensaje());

        long idUsuario = UsuarioLogeado();

        //No permite el mismo titulo y el mismo mensaje de otro topico ya existente
        if (idTopico == null) {

            Topico topico = topicoRepository.save(new Topico(datosRegistroTopico, idUsuario));

            DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),
                    topico.getMensaje(), topico.getFechaCreacion().toString(), topico.getEstatus(), topico.getAutor(),
                    topico.getCurso());

            URI uri = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(uri).body(datosRespuestaTopico);

        } else {

            return ResponseEntity.badRequest().body(" El titulo y el mensaje es el mismo que otro post, por favor modificalo");

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

        String idTopico = topicoRepository.SeleccionarIdPorTituloYMensaje(datosActualizarTopico.titulo(),
                datosActualizarTopico.mensaje());

        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());

        long idUsuario = UsuarioLogeado();

        if (idUsuario == topico.getAutor()) {

            //No permite el mismo titulo y el mismo mensaje de otro topico ya existente
            if (idTopico != null) {

                return ResponseEntity.badRequest().body(" El titulo y el mensaje es el mismo que otro post, " + "por favor modificalo");

            } else {

                topico.actualizarDatos(datosActualizarTopico);

                DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion().toString(), topico.getEstatus(), topico.getAutor(), topico.getCurso());

                return ResponseEntity.ok(datosRespuestaTopico);
            }

        } else {

            return ResponseEntity.badRequest().body("No puedes modificar el topico de alguien más");

        }

    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un topico")
    public ResponseEntity EliminarTopico(@PathVariable long id) {

        long idAutorTopico = topicoRepository.seleccionarAutorDeTopico(id);

        long idUsuario = UsuarioLogeado();

        if (idUsuario == idAutorTopico) {

            topicoRepository.deleteById(id);

            return ResponseEntity.ok("El topico con el id: " + id + " fue eliminado");

        } else {

            return ResponseEntity.badRequest().body("No puedes borrar el topico de alguien mas");

        }

    }


    @PutMapping("/deshabilitar/{id}")
    @Transactional
    @Operation(summary = "Deshabilitar un topico")
    public ResponseEntity deshabilitarTopico(@PathVariable long id) {

        Topico topico = topicoRepository.getReferenceById(id);

        long idUsuario = UsuarioLogeado();

        if (idUsuario == topico.getAutor()) {

            topico.Deshabilitar();

            return ResponseEntity.ok("El topico con el id: " + id + " fue deshabilitado");

        } else {

            return ResponseEntity.badRequest().body("No puedes deshabilitar el topico de alguien mas");

        }

    }

    public long UsuarioLogeado() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetail = (UserDetails) auth.getPrincipal();

        return usuarioRepository.getIdUsuarioByCorreo(userDetail.getUsername());

    }


}

