package com.example.biblioteca_digital.repository;

import com.example.biblioteca_digital.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioIdAndLivroId(Long usuarioId, Long livroId);
}