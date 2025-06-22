package com.example.biblioteca_digital.controller;

import com.example.biblioteca_digital.model.Compra;
import com.example.biblioteca_digital.model.Livro;
import com.example.biblioteca_digital.model.Usuario;
import com.example.biblioteca_digital.service.CompraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CompraControllerTest {

    @Mock
    private CompraService compraService;

    @InjectMocks
    private CompraController compraController;

    private Compra compra;
    private Usuario usuario;
    private Livro livro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario(1L, "Usuário Teste", "teste@email.com", "senha123", 100.0);
        livro = new Livro(1L, "Livro Teste", "Autor Teste", "Descrição", "Categoria", 50.0);
        compra = new Compra(1L, usuario, livro, LocalDateTime.now());
    }

    @Test
    void comprarLivro_Sucesso() {
        // Arrange
        when(compraService.comprarLivro(anyLong(), anyLong())).thenReturn(compra);

        // Act
        ResponseEntity<Compra> response = compraController.comprarLivro(1L, 1L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(compra, response.getBody());
        verify(compraService, times(1)).comprarLivro(1L, 1L);
        System.out.println(response);
    }

    @Test
    void comprarLivro_UsuarioNaoEncontrado() {
        // Arrange
        when(compraService.comprarLivro(anyLong(), anyLong()))
                .thenThrow(new RuntimeException("Usuário não encontrado"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> compraController.comprarLivro(99L, 1L)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(compraService, times(1)).comprarLivro(99L, 1L);
        System.out.println("Usuario nao encontrado.");
    }

    @Test
    void comprarLivro_LivroNaoEncontrado() {
        // Arrange
        when(compraService.comprarLivro(anyLong(), anyLong()))
                .thenThrow(new RuntimeException("Livro não encontrado"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> compraController.comprarLivro(1L, 99L)
        );

        assertEquals("Livro não encontrado", exception.getMessage());
        verify(compraService, times(1)).comprarLivro(1L, 99L);
        System.out.println("Livro nao encontrado.");
    }

    @Test
    void comprarLivro_SaldoInsuficiente() {
        // Arrange
        when(compraService.comprarLivro(anyLong(), anyLong()))
                .thenThrow(new RuntimeException("Saldo insuficiente"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> compraController.comprarLivro(1L, 1L)
        );

        assertEquals("Saldo insuficiente", exception.getMessage());
        verify(compraService, times(1)).comprarLivro(1L, 1L);
        System.out.println("Saldo insuficiente.");
    }

    @Test
    void comprarLivro_LivroJaComprado() {
        // Arrange
        when(compraService.comprarLivro(anyLong(), anyLong()))
                .thenThrow(new RuntimeException("Usuário já possui este livro"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> compraController.comprarLivro(1L, 1L)
        );

        assertEquals("Usuário já possui este livro", exception.getMessage());
        verify(compraService, times(1)).comprarLivro(1L, 1L);
        System.out.println("Usuario ja possui este livro.");
    }

    @Test
    void listarComprasPorUsuario_Sucesso() {
        // Arrange
        List<Compra> compras = Arrays.asList(compra);
        when(compraService.listarComprasPorUsuario(anyLong())).thenReturn(compras);

        // Act
        ResponseEntity<List<Compra>> response = compraController.listarComprasPorUsuario(1L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals(compra, response.getBody().get(0));
        verify(compraService, times(1)).listarComprasPorUsuario(1L);
        System.out.println(response);
    }

    @Test
    void listarComprasPorUsuario_ListaVazia() {
        // Arrange
        List<Compra> comprasVazia = List.of();
        when(compraService.listarComprasPorUsuario(anyLong())).thenReturn(comprasVazia);

        // Act
        ResponseEntity<List<Compra>> response = compraController.listarComprasPorUsuario(1L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
        verify(compraService, times(1)).listarComprasPorUsuario(1L);
        System.out.println(response);
    }

    @Test
    void listarComprasPorUsuario_UsuarioNaoEncontrado() {
        // Arrange
        when(compraService.listarComprasPorUsuario(anyLong()))
                .thenThrow(new RuntimeException("Usuário não encontrado"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> compraController.listarComprasPorUsuario(99L)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(compraService, times(1)).listarComprasPorUsuario(99L);
        System.out.println("Usuario nao encontrado.");
    }
}