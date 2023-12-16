package com.Amaya.ForoAlura.Controller;

import com.Amaya.ForoAlura.Repositorios.UsuarioRepository;
import com.Amaya.ForoAlura.domain.Usuario.DatosUsuario.DatosAutenticacionUsuario;
import com.Amaya.ForoAlura.domain.Usuario.Usuario;
import com.Amaya.ForoAlura.infra.Seguridad.DatosJWTToken;
import com.Amaya.ForoAlura.infra.Seguridad.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticacion", description = "obtiene el token para el usuario asignado que da acceso")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {

        Boolean usuarioBaneado = usuarioRepository.usuarioBaneadoPorCorreo(datosAutenticacionUsuario.correo());

        //Si el usuario esta baneado no puede hacer el Login
        if (!usuarioBaneado) {

            Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.correo(),
                    datosAutenticacionUsuario.contrasenha());
            var usuarioAutenticado = authenticationManager.authenticate(authToken);
            var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
            return ResponseEntity.ok(new DatosJWTToken(JWTtoken));

        } else {

            return ResponseEntity.badRequest().body("La cuenta a la que intentas acceder esta baneado del servidor");

        }


    }

}
