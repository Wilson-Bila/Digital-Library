package com.example.biblioteca_digital.controller;

import com.example.biblioteca_digital.model.Usuario;
import com.example.biblioteca_digital.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioSalvo = usuarioService.salvar(usuario);
        return ResponseEntity.ok(usuarioSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/adicionar-saldo")
    public ResponseEntity<Usuario> adicionarSaldo(
            @PathVariable Long id,
            @RequestParam double valor) {
        Usuario usuario = usuarioService.buscarPorId(id);
        usuario.setSaldo(usuario.getSaldo() + valor);
        Usuario usuarioAtualizado = usuarioService.salvar(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }
}