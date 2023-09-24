package com.Amaya.ForoAlura.domain.Topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico (

        @NotBlank(message = "El titulo no puede estar vacio")
        String titulo,

        @NotBlank(message = "El mensaje no puede estar vacio")
        String mensaje,

        @NotNull
        long autor,

        @NotBlank(message = "El curso no puede estar vacio")
        String curso

){
}
