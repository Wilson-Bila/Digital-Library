package com.example.biblioteca_digital.service;

import com.example.biblioteca_digital.dto.LivroDTO;
import com.example.biblioteca_digital.model.Livro;
import com.example.biblioteca_digital.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService livroService;

    private Livro livro;
    private LivroDTO livroDTO;

    @BeforeEach
    void setUp() {
        livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Dom Quixote");
        livro.setAutor("Miguel de Cervantes");
        livro.setDescricao("Clássico da literatura portuguesa");
        livro.setCategoria("Literatura");
        livro.setPreco(450.90);

        livroDTO = new LivroDTO();
        livroDTO.setTitulo("Dom Quixote");
        livroDTO.setAutor("Miguel de Cervantes");
        livroDTO.setDescricao("Clássico da literatura portuguesa");
        livroDTO.setCategoria("Literatura");
        livroDTO.setPreco(450.90);
    }

    @Test
    void listarTodos_DeveRetornarListaDeLivros() {
        // Arrange
        when(livroRepository.findAll()).thenReturn(Arrays.asList(livro));

        // Act
        List<Livro> result = livroService.listarTodos();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(livro.getTitulo(), result.get(0).getTitulo());
        verify(livroRepository, times(1)).findAll();
        System.out.println(result);
    }

    @Test
    void buscarPorId_QuandoLivroExiste_DeveRetornarLivro() {
        // Arrange
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        // Act
        Optional<Livro> result = livroService.buscarPorId(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(livro.getTitulo(), result.get().getTitulo());
        verify(livroRepository, times(1)).findById(1L);
        System.out.println(result);
    }

    @Test
    void buscarPorId_QuandoLivroNaoExiste_DeveRetornarVazio() {
        // Arrange
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Livro> result = livroService.buscarPorId(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(livroRepository, times(1)).findById(1L);
        System.out.println(result);
    }

    @Test
    void criar_DeveSalvarERetornarLivro() {
        // Arrange
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        // Act
        Livro result = livroService.criar(livroDTO);

        // Assert
        assertNotNull(result);
        assertEquals(livro.getTitulo(), result.getTitulo());
        assertEquals(livro.getAutor(), result.getAutor());
        verify(livroRepository, times(1)).save(any(Livro.class));
        System.out.println(result);
    }

    @Test
    void actualizar_QuandoLivroExiste_DeveAtualizarERetornarLivro() {
        // Arrange
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        // Modificar DTO para atualização
        livroDTO.setTitulo("Dom Quixote - Edição Especial");

        // Act
        Optional<Livro> result = livroService.actualizar(1L, livroDTO);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Dom Quixote - Edição Especial", result.get().getTitulo());
        verify(livroRepository, times(1)).findById(1L);
        verify(livroRepository, times(1)).save(any(Livro.class));
        System.out.println(result);
    }

    @Test
    void actualizar_QuandoLivroNaoExiste_DeveRetornarVazio() {
        // Arrange
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Livro> result = livroService.actualizar(1L, livroDTO);

        // Assert
        assertFalse(result.isPresent());
        verify(livroRepository, times(1)).findById(1L);
        verify(livroRepository, never()).save(any(Livro.class));
        System.out.println(result);
    }

    @Test
    void remover_ChamarDeleteDoRepository() {
        // Arrange
        doNothing().when(livroRepository).deleteById(1L);

        // Act
        livroService.remover(1L);

        // Assert
        verify(livroRepository, times(1)).deleteById(1L);
        System.out.println(livroService);
    }
}