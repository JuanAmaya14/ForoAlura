package com.Amaya.ForoAlura.domain.Respuestas;

import com.Amaya.ForoAlura.domain.Respuestas.DatosRespuesta.DatosActualizarRespuesta;
import com.Amaya.ForoAlura.domain.Respuestas.DatosRespuesta.DatosRegistroRespuesta;
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

    public Respuesta(DatosRegistroRespuesta datosRegistroRespuesta, long idUsuario) {

        this.mensajeRespuesta = datosRegistroRespuesta.mensajeRespuesta();
        this.fechaRespuesta = Date.from(Instant.now());
        this.idTopico = datosRegistroRespuesta.idTopico();
        this.idAutor = idUsuario;

    }

    public void modificarRespuesta(DatosActualizarRespuesta datosActualizarRespuesta) {

        if (datosActualizarRespuesta.mensajeRespuesta() != null) {

            this.mensajeRespuesta = datosActualizarRespuesta.mensajeRespuesta();

        }

    }

}
