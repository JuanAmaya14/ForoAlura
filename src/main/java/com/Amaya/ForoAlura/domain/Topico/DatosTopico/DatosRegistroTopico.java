package com.Amaya.ForoAlura.domain.Topico.DatosTopico;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroTopico(

        @NotBlank(message = "El titulo no puede estar vacio")
        String titulo,

        @NotBlank(message = "El mensaje no puede estar vacio")
        String mensaje,

        @NotBlank(message = "El curso no puede estar vacio")
        String curso

) {
}
