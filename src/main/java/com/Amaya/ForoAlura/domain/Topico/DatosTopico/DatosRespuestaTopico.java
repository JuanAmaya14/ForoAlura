package com.Amaya.ForoAlura.domain.Topico.DatosTopico;

public record DatosRespuestaTopico(long id, String titulo, String mensaje, String fechaCreacion, Boolean estatus,
                                   long autor, String curso) {
}
