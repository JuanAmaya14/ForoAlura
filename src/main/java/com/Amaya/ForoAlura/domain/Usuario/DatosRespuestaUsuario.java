package com.Amaya.ForoAlura.domain.Usuario;

public record DatosRespuestaUsuario(

        long id,

        String nombre,

        String correo,

        String contrasenha,

        String fechaCreacion,

        Boolean baneado) {
}
