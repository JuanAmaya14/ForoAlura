package com.Amaya.ForoAlura.domain;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record DatosRegistroTopico (

        @NotBlank
        String titulo,

        @NotBlank
        String mensaje,

        @NotBlank
        String estatus,

        @NotBlank
        String autor,

        @NotBlank
        String curso

){
}
