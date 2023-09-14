package com.Amaya.ForoAlura.domain.Topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizaTopico(

        @NotNull(message = "El id no puede ser vacio")
        long id,

        String titulo,

        String mensaje,

        String curso

) {
}
