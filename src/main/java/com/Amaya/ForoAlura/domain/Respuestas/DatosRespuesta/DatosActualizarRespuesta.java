package com.Amaya.ForoAlura.domain.Respuestas.DatosRespuesta;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarRespuesta(@NotNull long id, String mensajeRespuesta) {
}
