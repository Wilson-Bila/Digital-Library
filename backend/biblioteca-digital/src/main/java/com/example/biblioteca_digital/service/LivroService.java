package com.example.biblioteca_digital.service;

import com.example.biblioteca_digital.model.Livro;
import com.example.biblioteca_digital.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    public void deletar(Long id) {
        livroRepository.deleteById(id);
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Livro> buscarPorAutor(String autor) {
        return livroRepository.findByAutorContainingIgnoreCase(autor);
    }

    public List<Livro> buscarPorCategoria(String categoria) {
        return livroRepository.findByCategoriaContainingIgnoreCase(categoria);
    }
}