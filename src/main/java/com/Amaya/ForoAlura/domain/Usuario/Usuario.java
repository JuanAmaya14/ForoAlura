package com.Amaya.ForoAlura.domain.Usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.beans.BeanProperty;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    @Email(message = "El email debe ser una dirección de correo electrónico con formato correcto")
    private String correo;

    private String contrasenha;

    private Date fechaCreacion;

    private Boolean baneado;

    public Usuario(DatosRegistroUsuario datosRegistroUsuario, String contrasenha) {
        this.nombre = datosRegistroUsuario.nombre();
        this.correo = datosRegistroUsuario.correo();
        this.contrasenha = contrasenha;
        this.fechaCreacion = Date.from(Instant.now());
        this.baneado = false;
    }

    public void modificarDatos(DatosActualizarUsuario datosActualizarUsuario, String contrasenha) {

        if (datosActualizarUsuario.nombre() != null) {

            this.nombre = datosActualizarUsuario.nombre();

        }

        if (datosActualizarUsuario.correo() != null) {

            this.correo = datosActualizarUsuario.correo();

        }

        if(datosActualizarUsuario.contrasenha() != null){

            this.contrasenha = contrasenha;

        }
    }

    public void BanearUsuario() {

        this.baneado = true;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return contrasenha;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
