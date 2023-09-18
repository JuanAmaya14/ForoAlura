package com.Amaya.ForoAlura.Repositorios;

import com.Amaya.ForoAlura.domain.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByCorreo(String email);
}
