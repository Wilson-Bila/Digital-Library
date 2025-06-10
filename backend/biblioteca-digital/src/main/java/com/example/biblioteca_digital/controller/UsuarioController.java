package com.example.biblioteca_digital.controller;

import com.example.biblioteca_digital.dto.UsuarioDTO;
import com.example.biblioteca_digital.model.Usuario;
import com.example.biblioteca_digital.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.criar(usuarioDTO);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioActualizado = usuarioService.actualizar(id, usuarioDTO);
        return usuarioActualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        usuarioService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
