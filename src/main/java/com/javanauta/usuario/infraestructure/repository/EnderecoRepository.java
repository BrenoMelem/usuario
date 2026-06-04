package com.javanauta.usuario.infraestructure.repository;

import com.javanauta.usuario.infraestructure.entity.Enderecos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Enderecos,Long> {
}
