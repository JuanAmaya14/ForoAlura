package com.Amaya.ForoAlura.domain.Respuestas;

public record DatosRespuestaRespuesta(
        long id,
        String mensajeRespuesta,
        String fechaRespuesta,
        long idTopico,
        long idAutor) {
}
