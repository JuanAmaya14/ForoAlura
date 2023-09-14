package com.Amaya.ForoAlura.domain.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarUsuario(

        @NotNull(message = "El id no puede estar vacio")
        long id,

        String nombre,

        @Email(message = "El email debe ser una dirección de correo electrónico con formato correcto")
        String correo,

        String contrasenha) {
}
