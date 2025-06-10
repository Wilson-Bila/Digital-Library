package com.example.biblioteca_digital.dto;

import lombok.Data;

@Data
public class LivroDTO {
    private String titulo;
    private String autor;
    private String descricao;
    private String categoria;
    private Double preco;
}
