package com.example.biblioteca_digital.repository;

import com.example.biblioteca_digital.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
        Optional<Usuario> findByEmail(String email);
    }