package com.Amaya.ForoAlura.domain;

public record DatosListadoTopico (

        long id,

        String titulo,

        String mensaje,

        String fechaCreacion,

        String estatus,

        String autor,

        String curso
){
}
