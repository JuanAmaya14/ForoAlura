package com.Amaya.ForoAlura.Repositorios;

import com.Amaya.ForoAlura.domain.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

//Repositorio de Usuario
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByCorreo(String email);

    @Query("""
            select b.baneado from Usuario b where b.correo=:correo
            """)
    Boolean usuarioBaneadoPorCorreo(String correo);

    @Query("""
            select b.baneado from Usuario b where b.id=:id
            """)
    Boolean usuarioBaneadoPorId(long id);
}
