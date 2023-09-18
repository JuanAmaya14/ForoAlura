package com.Amaya.ForoAlura.Controller;

import com.Amaya.ForoAlura.Repositorios.UsuarioRepository;
import com.Amaya.ForoAlura.domain.Usuario.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@EnableWebSecurity
public class UsuarioController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/registro")
    @Transactional
    public ResponseEntity RegistrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario,
                                           UriComponentsBuilder uriComponentsBuilder) {

        String contrasenha = passwordEncoder.encode(datosRegistroUsuario.contrasenha());

        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario, contrasenha));

        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(usuario.getId(), usuario.getNombre(),
                usuario.getCorreo(), usuario.getContrasenha(), usuario.getFechaCreacion().toString(), usuario.getBaneado());

        URI uri = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(datosRespuestaUsuario);

    }

    @GetMapping
    public List<Usuario> ListarUsuarios() {

        return usuarioRepository.findAll();

    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoUsuario> ListarUsuarioPorId(@PathVariable long id) {

        Usuario usuario = usuarioRepository.getReferenceById(id);

        DatosListadoUsuario datosListadoUsuario = new DatosListadoUsuario(usuario.getNombre(),
                usuario.getFechaCreacion().toString());

        return ResponseEntity.ok(datosListadoUsuario);

    }

    @PutMapping("/actualizar")
    @Transactional
    public ResponseEntity modificarUsuario(@RequestBody DatosActualizarUsuario datosActualizarUsuario) {

        Usuario usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id());

        String contrasenha = passwordEncoder.encode(datosActualizarUsuario.contrasenha());

        usuario.modificarDatos(datosActualizarUsuario, contrasenha);

        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(usuario.getId(),
                usuario.getNombre(), usuario.getCorreo(), usuario.getContrasenha(),
                usuario.getFechaCreacion().toString(), usuario.getBaneado());

        return ResponseEntity.ok(datosRespuestaUsuario);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable long id) {

        usuarioRepository.deleteById(id);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/banear/{id}")
    @Transactional
    public ResponseEntity BanearUsuario(@PathVariable long id) {

        Usuario usuario = usuarioRepository.getReferenceById(id);

        usuario.BanearUsuario();

        return ResponseEntity.ok("El usuario " + usuario.getNombre() + " fue baneado exitosamente");

    }

}
