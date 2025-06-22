package com.example.biblioteca_digital.controller;

import com.example.biblioteca_digital.model.Livro;
import com.example.biblioteca_digital.service.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LivroControllerTest {

    @Mock
    private LivroService livroService;

    @InjectMocks
    private LivroController livroController;

    private Livro livro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        livro = new Livro(1L, "Dom Casmurro", "Machado de Assis",
                "Romance africano", "Literatura", 599.90);
    }

    @Test
    void criarLivro() {
        when(livroService.salvar(any(Livro.class))).thenReturn(livro);

        ResponseEntity<Livro> response = livroController.criarLivro(livro);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dom Casmurro", response.getBody().getTitulo());
        verify(livroService, times(1)).salvar(livro);
        System.out.println(response);
    }

    @Test
    void listarLivros() {
        when(livroService.listarTodos()).thenReturn(Arrays.asList(livro));

        ResponseEntity<List<Livro>> response = livroController.listarLivros();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        verify(livroService, times(1)).listarTodos();
        System.out.println(response);
    }

    @Test
    void buscarLivroPorId() {
        when(livroService.buscarPorId(1L)).thenReturn(livro);

        ResponseEntity<Livro> response = livroController.buscarLivroPorId(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dom Casmurro", response.getBody().getTitulo());
        verify(livroService, times(1)).buscarPorId(1L);
        System.out.println(response);
    }

    @Test
    void deletarLivro() {
        doNothing().when(livroService).deletar(1L);

        ResponseEntity<Void> response = livroController.deletarLivro(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(livroService, times(1)).deletar(1L);
        System.out.println(response);
    }

    @Test
    void buscarPorTitulo() {
        when(livroService.buscarPorTitulo("Dom")).thenReturn(Arrays.asList(livro));

        ResponseEntity<List<Livro>> response = livroController.buscarPorTitulo("Dom");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        verify(livroService, times(1)).buscarPorTitulo("Dom");
        System.out.println(response);
    }

    @Test
    void buscarPorAutor() {
        when(livroService.buscarPorAutor("Machado")).thenReturn(Arrays.asList(livro));

        ResponseEntity<List<Livro>> response = livroController.buscarPorAutor("Machado");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        verify(livroService, times(1)).buscarPorAutor("Machado");
        System.out.println(response);
    }

    @Test
    void buscarPorCategoria() {
        when(livroService.buscarPorCategoria("Literatura")).thenReturn(Arrays.asList(livro));

        ResponseEntity<List<Livro>> response = livroController.buscarPorCategoria("Literatura");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        verify(livroService, times(1)).buscarPorCategoria("Literatura");
        System.out.println(response);
    }
}