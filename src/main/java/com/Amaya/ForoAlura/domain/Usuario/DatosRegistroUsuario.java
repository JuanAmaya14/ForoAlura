package com.Amaya.ForoAlura.domain.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosRegistroUsuario (
        @NotBlank
        String nombre,

        @NotBlank
        @Email
        String correo,

        @NotBlank
        String contrasenha){
}
