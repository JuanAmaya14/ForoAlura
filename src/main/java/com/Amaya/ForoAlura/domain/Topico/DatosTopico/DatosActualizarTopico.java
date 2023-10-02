package com.Amaya.ForoAlura.domain.Topico.DatosTopico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(

        @NotNull(message = "El id no puede ser vacio")
        long id,

        String titulo,

        String mensaje,

        String curso

) {
}
