package com.Amaya.ForoAlura.domain.Respuestas.DatosRespuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuesta(

        @NotBlank
        String mensajeRespuesta,

        @NotNull
        long idTopico) {
}
