package com.Amaya.ForoAlura.Controller;

import com.Amaya.ForoAlura.Repositorios.RespuestaRepository;
import com.Amaya.ForoAlura.domain.Respuestas.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/respuesta")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @GetMapping
    public List<Respuesta> listarRespuestas() {

        return respuestaRepository.findAll();

    }

    @GetMapping("/{id}")
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
    public ResponseEntity registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta,
                                             UriComponentsBuilder uriComponentsBuilder) {

        Respuesta respuesta = respuestaRepository.save(new Respuesta(datosRegistroRespuesta));

        DatosRespuestaRespuesta datosRespuestaRespuesta = new DatosRespuestaRespuesta(respuesta.getId(),
                respuesta.getMensajeRespuesta(), respuesta.getFechaRespuesta().toString(), respuesta.getIdTopico(),
                respuesta.getIdAutor());

        URI uri = uriComponentsBuilder.path("/respuesta/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(uri).body(datosRespuestaRespuesta);


    }


    @PutMapping
    @Transactional
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
    public ResponseEntity eliminarRespuesta(@PathVariable long id){

        respuestaRepository.deleteById(id);

        return ResponseEntity.ok("La respuesta con el id " + id + " fue eliminado con exito");

    }

}
