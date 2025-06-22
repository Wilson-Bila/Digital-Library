package com.example.biblioteca_digital.controller;

import com.example.biblioteca_digital.model.Compra;
import com.example.biblioteca_digital.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping("/usuario/{usuarioId}/livro/{livroId}")
    public ResponseEntity<Compra> comprarLivro(@PathVariable Long usuarioId, @PathVariable Long livroId) {
        Compra compra = compraService.comprarLivro(usuarioId, livroId);
        return ResponseEntity.ok(compra);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Compra>> listarComprasPorUsuario(@PathVariable Long usuarioId) {
        List<Compra> compras = compraService.listarComprasPorUsuario(usuarioId);
        return ResponseEntity.ok(compras);
    }
}