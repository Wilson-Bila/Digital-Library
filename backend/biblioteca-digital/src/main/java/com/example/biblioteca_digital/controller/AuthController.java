package com.example.biblioteca_digital.controller;

import com.example.biblioteca_digital.model.Usuario;
import com.example.biblioteca_digital.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario loginRequest) {
        Usuario usuario = usuarioService.buscarPorEmail(loginRequest.getEmail());

        if (!usuario.getSenha().equals(loginRequest.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        return ResponseEntity.ok(usuario);
    }
}