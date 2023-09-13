package com.Amaya.ForoAlura.domain.Topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizaTopico(

        @NotNull
        long id,

        String titulo,

        String mensaje,

        String autor,

        String curso

) {
}
