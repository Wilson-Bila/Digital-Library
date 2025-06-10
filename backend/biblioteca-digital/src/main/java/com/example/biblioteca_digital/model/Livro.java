package com.example.biblioteca_digital.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private Double preco;
}
