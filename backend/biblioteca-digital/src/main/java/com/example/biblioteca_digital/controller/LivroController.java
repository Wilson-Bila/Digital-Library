package com.example.biblioteca_digital.controller;

import com.example.biblioteca_digital.dto.LivroDTO;
import com.example.biblioteca_digital.model.Livro;
import com.example.biblioteca_digital.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<Livro> listarTodos() {
        return livroService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        Optional<Livro> livro = livroService.buscarPorId(id);
        return livro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Livro criar(@RequestBody LivroDTO livroDTO) {
        return livroService.criar(livroDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> actualizar(@PathVariable Long id, @RequestBody LivroDTO livroDTO) {
        Optional<Livro> livroActualizado = livroService.actualizar(id, livroDTO);
        return livroActualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerr(@PathVariable Long id) {
        livroService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
