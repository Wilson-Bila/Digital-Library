package com.example.biblioteca_digital.repository;

import com.example.biblioteca_digital.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
