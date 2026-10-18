package com.veterinaria.patitas_felices.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.veterinaria.patitas_felices.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    List<Usuario> findByNombreUsuarioContainingIgnoreCase(String nombreUsuario);

    Optional<Usuario> findByEmailUsuario(String emailUsuario);

}
