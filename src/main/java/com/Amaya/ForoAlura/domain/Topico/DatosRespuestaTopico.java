package com.Amaya.ForoAlura.domain.Topico;

public record DatosRespuestaTopico(long id, String titulo, String mensaje, String fechaCreacion, Boolean estatus,
                                   String autor, String curso) {
}