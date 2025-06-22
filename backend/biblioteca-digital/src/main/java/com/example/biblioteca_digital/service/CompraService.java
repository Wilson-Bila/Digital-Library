package com.example.biblioteca_digital.service;

import com.example.biblioteca_digital.model.Compra;
import com.example.biblioteca_digital.model.Livro;
import com.example.biblioteca_digital.model.Usuario;
import com.example.biblioteca_digital.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LivroService livroService;

    @Transactional
    public Compra comprarLivro(Long usuarioId, Long livroId) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        Livro livro = livroService.buscarPorId(livroId);

        // Verificar se o usuário já comprou o livro
        if (compraRepository.existsByUsuarioIdAndLivroId(usuarioId, livroId)) {
            throw new RuntimeException("Usuário já possui este livro");
        }

        // Verificar saldo suficiente
        if (usuario.getSaldo() < livro.getPreco()) {
            throw new RuntimeException("Saldo insuficiente");
        }

        // Atualizar saldo do usuário
        usuario.setSaldo(usuario.getSaldo() - livro.getPreco());
        usuarioService.salvar(usuario);

        // Registrar a compra
        Compra compra = new Compra();
        compra.setUsuario(usuario);
        compra.setLivro(livro);
        compra.setDataCompra(LocalDateTime.now());

        return compraRepository.save(compra);
    }

    public List<Compra> listarComprasPorUsuario(Long usuarioId) {
        return compraRepository.findByUsuarioId(usuarioId);
    }
}