package com.javanauta.usuario.infraestructure.repository;

import com.javanauta.usuario.infraestructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository extends JpaRepository <Telefone, Long> {
}
