package com.Amaya.ForoAlura.domain.Usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    @Email(message = "El email debe ser una dirección de correo electrónico con formato correcto")
    private String correo;

    private String contrasenha;

    private Date fechaCreacion;

    private Boolean baneado;

    public Usuario(DatosRegistroUsuario datosRegistroUsuario) {
        this.nombre = datosRegistroUsuario.nombre();
        this.correo = datosRegistroUsuario.correo();
        this.contrasenha = datosRegistroUsuario.contrasenha();
        this.fechaCreacion = Date.from(Instant.now());
        this.baneado = false;
    }

    public void modificarDatos(DatosActualizarUsuario datosActualizarUsuario) {

        if (datosActualizarUsuario.nombre() != null) {

            this.nombre = datosActualizarUsuario.nombre();

        }

        if (datosActualizarUsuario.correo() != null) {

            this.correo = datosActualizarUsuario.correo();

        }

        if(datosActualizarUsuario.contrasenha() != null){

            this.contrasenha = datosActualizarUsuario.contrasenha();

        }
    }

    public void BanearUsuario() {

        this.baneado = true;

    }
}
