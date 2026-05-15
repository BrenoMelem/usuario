package com.javanauta.usuario.infraestructure.repository;

import com.javanauta.usuario.infraestructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  UsuarioRepository {
    boolean existsByEmail (String email);
    Optional<Usuario> findByEmail (String email);
    @Transactional
    void deleteByEmail (String email);

}
