package com.example.biblioteca_digital.service;

import com.example.biblioteca_digital.model.Compra;
import com.example.biblioteca_digital.model.Livro;
import com.example.biblioteca_digital.model.Usuario;
import com.example.biblioteca_digital.repository.CompraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private LivroService livroService;

    @InjectMocks
    private CompraService compraService;

    private Usuario usuario;
    private Livro livro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario(1L, "Teste", "teste@email.com", "senha", 100.0);
        livro = new Livro(1L, "Livro Teste", "Autor Teste", "Descrição", "Categoria", 50.0);
    }

    @Test
    void comprarLivro_Sucesso() {
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);
        when(livroService.buscarPorId(1L)).thenReturn(livro);
        when(compraRepository.existsByUsuarioIdAndLivroId(1L, 1L)).thenReturn(false);
        when(compraRepository.save(any(Compra.class))).thenReturn(new Compra());

        Compra compra = compraService.comprarLivro(1L, 1L);

        assertNotNull(compra);
        verify(usuarioService).salvar(usuario);
        assertEquals(50.0, usuario.getSaldo());
        System.out.println(compra);
    }

    @Test
    void comprarLivro_UsuarioJaPossuiLivro() {
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);
        when(livroService.buscarPorId(1L)).thenReturn(livro);
        when(compraRepository.existsByUsuarioIdAndLivroId(1L, 1L)).thenReturn(true);

        assertThrows(RuntimeException.class, () -> compraService.comprarLivro(1L, 1L));
        System.out.println("O usuario ja possui este livro.");
    }

    @Test
    void comprarLivro_SaldoInsuficiente() {
        usuario.setSaldo(10.0);
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);
        when(livroService.buscarPorId(1L)).thenReturn(livro);
        when(compraRepository.existsByUsuarioIdAndLivroId(1L, 1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> compraService.comprarLivro(1L, 1L));
        System.out.println("Saldo Insuficiente!");
    }

    @Test
    void listarComprasPorUsuario() {
        when(compraRepository.findByUsuarioId(1L)).thenReturn(Collections.singletonList(new Compra()));

        List<Compra> compras = compraService.listarComprasPorUsuario(1L);

        assertFalse(compras.isEmpty());
        System.out.println(compras);
    }
}