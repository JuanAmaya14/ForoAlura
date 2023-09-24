package com.Amaya.ForoAlura.Repositorios;

import com.Amaya.ForoAlura.domain.Topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//Repositorio de topico
@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    @Query("""
            select t.id from Topico t where t.titulo = :titulo and t.mensaje = :mensaje
            """)
    String SeleccionarIdPorTituloYMensaje(String titulo, String mensaje);


    @Query("""
            select e.estatus from Topico e where e.id=:idTopico
            """)
    Boolean estaDeshabilitado(long idTopico);
}
