package com.Amaya.ForoAlura.Repositorios;

import com.Amaya.ForoAlura.domain.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico,Long> {

    @Query("""
            select t.titulo from Topico t where t.titulo=:titulo
            """)
    String SeleccionarPorTitulo(String titulo);

    @Query("""
            select m.mensaje from Topico m where m.mensaje=:mensaje
            """)
    String SeleccionarPorMensaje(String mensaje);
}
