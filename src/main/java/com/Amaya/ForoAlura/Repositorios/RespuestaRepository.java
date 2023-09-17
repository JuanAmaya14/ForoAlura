package com.Amaya.ForoAlura.Repositorios;

import com.Amaya.ForoAlura.domain.Respuestas.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {


    @Query("""
            select r from Respuesta r where r.idTopico = :idTopico
            """)
    ArrayList<Respuesta> taerDatosPorIdTopico(long idTopico);


    @Query("""
            select t.titulo from Topico t where t.id = :idTopico
            """)
    String tituloTopicoPorId(long idTopico);


    @Query("""
            select u.nombre from Usuario u where u.id = :idAutor
            """)
    String autorRespuestaPorId(long idAutor);
}
