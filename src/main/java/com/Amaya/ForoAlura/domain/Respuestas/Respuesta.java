package com.Amaya.ForoAlura.domain.Respuestas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;


@Table(name = "respuestas")
@Entity(name = "Respuesta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String mensajeRespuesta;

    private Date fechaRespuesta;

    private long idTopico;

    private long idAutor;

    public Respuesta(DatosRegistroRespuesta datosRegistroRespuesta) {

        this.mensajeRespuesta = datosRegistroRespuesta.mensajeRespuesta();
        this.fechaRespuesta = Date.from(Instant.now());
        this.idTopico = datosRegistroRespuesta.idTopico();
        this.idAutor = datosRegistroRespuesta.idAutor();

    }

    public void modificarRespuesta(DatosActualizarRespuesta datosActualizarRespuesta) {

        if (datosActualizarRespuesta.mensajeRespuesta() != null) {

            this.mensajeRespuesta = datosActualizarRespuesta.mensajeRespuesta();

        }

    }

}
