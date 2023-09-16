package com.Amaya.ForoAlura.domain.Respuestas;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarRespuesta (@NotNull long id, String mensajeRespuesta){
}
