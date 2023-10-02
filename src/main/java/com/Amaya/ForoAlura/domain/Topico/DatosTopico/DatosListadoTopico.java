package com.Amaya.ForoAlura.domain.Topico.DatosTopico;

import com.Amaya.ForoAlura.domain.Respuestas.Respuesta;

import java.util.ArrayList;

public record DatosListadoTopico(String titulo, String mensaje, String fechaCreacion, Boolean estatus,
                                 long autor, String curso, ArrayList<Respuesta> mensajeRespuesta) {
}
