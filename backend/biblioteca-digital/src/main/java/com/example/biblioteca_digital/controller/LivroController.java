package com.example.biblioteca_digital.controller;

import com.example.biblioteca_digital.model.Livro;
import com.example.biblioteca_digital.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    public ResponseEntity<Livro> criarLivro(@RequestBody Livro livro) {
        Livro livroSalvo = livroService.salvar(livro);
        return ResponseEntity.ok(livroSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        List<Livro> livros = livroService.listarTodos();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarLivroPorId(@PathVariable Long id) {
        Livro livro = livroService.buscarPorId(id);
        return ResponseEntity.ok(livro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Livro>> buscarPorTitulo(@PathVariable String titulo) {
        List<Livro> livros = livroService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<Livro>> buscarPorAutor(@PathVariable String autor) {
        List<Livro> livros = livroService.buscarPorAutor(autor);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Livro>> buscarPorCategoria(@PathVariable String categoria) {
        List<Livro> livros = livroService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(livros);
    }
}