package com.example.biblioteca_digital.controller;

import com.example.biblioteca_digital.dto.LivroDTO;
import com.example.biblioteca_digital.model.Livro;
import com.example.biblioteca_digital.service.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroControllerTest {

    @Mock
    private LivroService livroService;

    @InjectMocks
    private LivroController livroController;

    private Livro livro;
    private LivroDTO livroDTO;

    @BeforeEach
    void setUp() {
        livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("1984");
        livro.setAutor("George Orwell");
        livro.setDescricao("Romance distópico");
        livro.setCategoria("Ficção Científica");
        livro.setPreco(39.90);

        livroDTO = new LivroDTO();
        livroDTO.setTitulo("1984");
        livroDTO.setAutor("George Orwell");
        livroDTO.setDescricao("Romance distópico");
        livroDTO.setCategoria("Ficção Científica");
        livroDTO.setPreco(39.90);
    }

    @Test
    @DisplayName("Deve retornar uma lista de livros")
    void listarTodosLivros() {
        // Arrange
        when(livroService.listarTodos()).thenReturn(Arrays.asList(livro));

        // Act
        List<Livro> result = livroController.listarTodos();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(livro.getTitulo(), result.get(0).getTitulo());
        verify(livroService, times(1)).listarTodos();
        System.out.println(result);
        System.out.println("Teste para listar-Todos-Livros passou com sucesso.");
    }

    @Test
    @DisplayName("Deve retornar OK quando o livro existe")
    void buscarPorId_QuandoLivroExiste_DeveRetornarOk() {
        // Arrange
        when(livroService.buscarPorId(1L)).thenReturn(Optional.of(livro));

        // Act
        ResponseEntity<Livro> response = livroController.buscarPorId(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(livro.getTitulo(), response.getBody().getTitulo());
        verify(livroService, times(1)).buscarPorId(1L);
        System.out.println(response);
        System.out.println("Teste buscarPorId_QuandoLivroExiste_RetornaOk passou com sucesso.");
    }

    @Test
    @DisplayName("Deve retornar NOT FOUND quando o livro não existe")
    void buscarPorId_QuandoLivroNaoExiste_RetornarNotFound() {
        // Arrange
        when(livroService.buscarPorId(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Livro> response = livroController.buscarPorId(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(livroService, times(1)).buscarPorId(1L);
        System.out.println(response);
        System.out.println("Teste buscarPorId_RetornarNotFound passou com sucesso.");
    }

    @Test
    @DisplayName("Deve criar um livro com dados válidos")
    void criar_RetornarLivroCriado() {
        // Arrange
        when(livroService.criar(any(LivroDTO.class))).thenReturn(livro);

        // Act
        Livro result = livroController.criar(livroDTO);

        // Assert
        assertNotNull(result);
        assertEquals(livro.getTitulo(), result.getTitulo());
        verify(livroService, times(1)).criar(any(LivroDTO.class));
        System.out.println(result);
        System.out.println("Teste criar_ComDadosValidos_RetornarLivroCriado passou com sucesso.");
    }

    @Test
    @DisplayName("Deve actualizar livro existente")
    void actualizar_QuandoLivroExiste_RetornarOk() {
        // Arrange
        when(livroService.actualizar(anyLong(), any(LivroDTO.class)))
                .thenReturn(Optional.of(livro));

        // Act
        ResponseEntity<Livro> response = livroController.actualizar(1L, livroDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(livro.getTitulo(), response.getBody().getTitulo());
        verify(livroService, times(1)).actualizar(anyLong(), any(LivroDTO.class));
        System.out.println(response);
    }

    @Test
    void actualizar_QuandoLivroNaoExiste_DeveRetornarNotFound() {
        // Arrange
        when(livroService.actualizar(anyLong(), any(LivroDTO.class)))
                .thenReturn(Optional.empty());

        // Act
        ResponseEntity<Livro> response = livroController.actualizar(1L, livroDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(livroService, times(1)).actualizar(anyLong(), any(LivroDTO.class));
        System.out.println(response);
    }

    @Test
    void removerr_DeveRetornarNoContent() {
        // Arrange
        doNothing().when(livroService).remover(1L);

        // Act
        ResponseEntity<Void> response = livroController.removerr(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(livroService, times(1)).remover(1L);
        System.out.println(response);
    }
}