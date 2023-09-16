package com.Amaya.ForoAlura.Repositorios;

import com.Amaya.ForoAlura.domain.Respuestas.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

}
