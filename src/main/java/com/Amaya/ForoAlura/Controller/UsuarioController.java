package com.Amaya.ForoAlura.Controller;

import com.Amaya.ForoAlura.Repositorios.UsuarioRepository;
import com.Amaya.ForoAlura.domain.Usuario.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuario")
@EnableWebSecurity
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuario")
public class UsuarioController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/registro")
    @Transactional
    @Operation(summary = "Registra un usuario")
    public ResponseEntity RegistrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario,
                                           UriComponentsBuilder uriComponentsBuilder) {

        //Encripta la contrasenha
        String contrasenha = passwordEncoder.encode(datosRegistroUsuario.contrasenha());

        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario, contrasenha));

        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(usuario.getId(), usuario.getNombre(),
                usuario.getCorreo(), usuario.getContrasenha(), usuario.getFechaCreacion().toString(), usuario.getBaneado());

        URI uri = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(datosRespuestaUsuario);

    }

    @GetMapping
    @Operation(summary = "Devuelve todos los usuarios existentes")
    public ResponseEntity ListarUsuarios() {

        return ResponseEntity.ok(usuarioRepository.findAll());


    }

    @GetMapping("/{id}")
    @Operation(summary = "Devuelve un usuario por el id")
    public ResponseEntity<DatosListadoUsuario> ListarUsuarioPorId(@PathVariable long id) {

        Usuario usuario = usuarioRepository.getReferenceById(id);

        DatosListadoUsuario datosListadoUsuario = new DatosListadoUsuario(usuario.getNombre(),
                usuario.getFechaCreacion().toString());

        return ResponseEntity.ok(datosListadoUsuario);

    }

    @PutMapping("/actualizar")
    @Transactional
    @Operation(summary = "Modifica un usuario")
    public ResponseEntity modificarUsuario(@RequestBody DatosActualizarUsuario datosActualizarUsuario) {

        Usuario usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id());

        //Encripta la contrasenha
        String contrasenha = passwordEncoder.encode(datosActualizarUsuario.contrasenha());

        usuario.modificarDatos(datosActualizarUsuario, contrasenha);

        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(usuario.getId(),
                usuario.getNombre(), usuario.getCorreo(), usuario.getContrasenha(),
                usuario.getFechaCreacion().toString(), usuario.getBaneado());

        return ResponseEntity.ok(datosRespuestaUsuario);

    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un usuario")
    public ResponseEntity eliminarUsuario(@PathVariable long id) {

        usuarioRepository.deleteById(id);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/banear/{id}")
    @Transactional
    @Operation(summary = "Banea a un usuario (deshabilitar la cuenta)")
    public ResponseEntity BanearUsuario(@PathVariable long id) {


        Boolean usuarioBaneado = usuarioRepository.usuarioBaneadoPorId(id);

        //Si el usuario no esta baneado entonces lo banea
        if (!usuarioBaneado) {

            Usuario usuario = usuarioRepository.getReferenceById(id);

            usuario.BanearUsuario();

            return ResponseEntity.ok("El usuario " + usuario.getNombre() + " fue baneado exitosamente");

        } else {

            return ResponseEntity.badRequest().body("El usuario ya estaba baneado");

        }


    }

    @PutMapping("/desbanear/{id}")
    @Transactional
    @Operation(summary = "Desbanea a un usuario (habilitar la cuenta)")
    public ResponseEntity DesbanearUsuario(@PathVariable long id) {

        Boolean usuarioBaneado = usuarioRepository.usuarioBaneadoPorId(id);

        //Si el usuario esta baneado entonces lo desbanea
        if (usuarioBaneado) {

            Usuario usuario = usuarioRepository.getReferenceById(id);

            usuario.DesbanearUsuario();

            return ResponseEntity.ok("El usuario " + usuario.getNombre() + " fue desbaneado exitosamente");


        } else {

            return ResponseEntity.badRequest().body("El usuario no esta baneado");

        }

    }

}
