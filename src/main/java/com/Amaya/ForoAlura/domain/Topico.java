package com.Amaya.ForoAlura.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;

    private String mensaje;

    private Date fechaCreacion;

    private Boolean estatus;

    private String autor;

    private String curso;

    public Topico(DatosRegistroTopico datosRegistroTopico) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.fechaCreacion = Date.from(Instant.now());
        this.estatus = true;
        this.autor = datosRegistroTopico.autor();
        this.curso = datosRegistroTopico.curso();
    }

    public void actualizarDatos(DatosActualizaTopico datosActualizaTopico) {

        if (datosActualizaTopico.titulo() != null) {

            this.titulo = datosActualizaTopico.titulo();

        }

        if (datosActualizaTopico.mensaje() != null) {

            this.mensaje = datosActualizaTopico.mensaje();

        }

        if (datosActualizaTopico.autor() != null) {

            this.autor = datosActualizaTopico.autor();

        }

        if (datosActualizaTopico.curso() != null) {

            this.curso = datosActualizaTopico.curso();

        }

    }
}
