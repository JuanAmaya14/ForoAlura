package com.Amaya.ForoAlura.domain.Topico;

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

    private long autor;

    private String curso;

    public Topico(DatosRegistroTopico datosRegistroTopico) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.fechaCreacion = Date.from(Instant.now());
        this.estatus = true;
        this.autor = datosRegistroTopico.autor();
        this.curso = datosRegistroTopico.curso();
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico) {

        if (datosActualizarTopico.titulo() != null) {

            this.titulo = datosActualizarTopico.titulo();

        }

        if (datosActualizarTopico.mensaje() != null) {

            this.mensaje = datosActualizarTopico.mensaje();

        }

        if (datosActualizarTopico.curso() != null) {

            this.curso = datosActualizarTopico.curso();

        }

    }

    public void Deshabilitar() {

        this.estatus = false;

    }
}
