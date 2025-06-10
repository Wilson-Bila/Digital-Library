package com.example.biblioteca_digital.service;

import com.example.biblioteca_digital.dto.LivroDTO;
import com.example.biblioteca_digital.model.Livro;
import com.example.biblioteca_digital.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    public Livro criar(LivroDTO livroDTO) {
        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAutor(livroDTO.getAutor());
        livro.setDescricao(livroDTO.getDescricao());
        livro.setCategoria(livroDTO.getCategoria());
        livro.setPreco(livroDTO.getPreco());
        return livroRepository.save(livro);
    }

    public Optional<Livro> actualizar(Long id, LivroDTO livroDTO) {
        return livroRepository.findById(id).map(livro -> {
            livro.setTitulo(livroDTO.getTitulo());
            livro.setAutor(livroDTO.getAutor());
            livro.setDescricao(livroDTO.getDescricao());
            livro.setCategoria(livroDTO.getCategoria());
            livro.setPreco(livroDTO.getPreco());
            return livroRepository.save(livro);
        });
    }

    public void remover(Long id) {
        livroRepository.deleteById(id);
    }
}