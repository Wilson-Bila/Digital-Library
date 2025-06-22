package com.example.biblioteca_digital.service;

import com.example.biblioteca_digital.model.Livro;
import com.example.biblioteca_digital.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService livroService;

    private Livro livro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        livro = new Livro(1L, "Dom Casmurro", "Machado de Assis",
                "Romance brasileiro", "Literatura", 29.90);
    }

    @Test
    void salvarLivro() {
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        Livro livroSalvo = livroService.salvar(livro);

        assertNotNull(livroSalvo);
        assertEquals("Dom Casmurro", livroSalvo.getTitulo());
        verify(livroRepository, times(1)).save(livro);
        System.out.println(livroSalvo);
    }

    @Test
    void listarTodosLivros() {
        when(livroRepository.findAll()).thenReturn(Arrays.asList(livro));

        List<Livro> livros = livroService.listarTodos();

        assertFalse(livros.isEmpty());
        assertEquals(1, livros.size());
        verify(livroRepository, times(1)).findAll();
        System.out.println(livros);
    }

    @Test
    void buscarLivroPorId() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        Livro livroEncontrado = livroService.buscarPorId(1L);

        assertNotNull(livroEncontrado);
        assertEquals("Dom Casmurro", livroEncontrado.getTitulo());
        verify(livroRepository, times(1)).findById(1L);
        System.out.println(livroEncontrado);
    }

    @Test
    void buscarLivroPorIdNaoEncontrado() {
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> livroService.buscarPorId(1L));
        verify(livroRepository, times(1)).findById(1L);
    }

    @Test
    void deletarLivro() {
        doNothing().when(livroRepository).deleteById(1L);

        livroService.deletar(1L);

        verify(livroRepository, times(1)).deleteById(1L);
    }

    @Test
    void buscarPorTitulo() {
        when(livroRepository.findByTituloContainingIgnoreCase("Dom"))
                .thenReturn(Arrays.asList(livro));

        List<Livro> livros = livroService.buscarPorTitulo("Dom");

        assertFalse(livros.isEmpty());
        assertEquals(1, livros.size());
        verify(livroRepository, times(1)).findByTituloContainingIgnoreCase("Dom");
        System.out.println(livros);
    }

    @Test
    void buscarPorAutor() {
        when(livroRepository.findByAutorContainingIgnoreCase("Machado"))
                .thenReturn(Arrays.asList(livro));

        List<Livro> livros = livroService.buscarPorAutor("Machado");

        assertFalse(livros.isEmpty());
        assertEquals(1, livros.size());
        verify(livroRepository, times(1)).findByAutorContainingIgnoreCase("Machado");
        System.out.println(livros);
    }

    @Test
    void buscarPorCategoria() {
        when(livroRepository.findByCategoriaContainingIgnoreCase("Literatura"))
                .thenReturn(Arrays.asList(livro));

        List<Livro> livros = livroService.buscarPorCategoria("Literatura");

        assertFalse(livros.isEmpty());
        assertEquals(1, livros.size());
        verify(livroRepository, times(1)).findByCategoriaContainingIgnoreCase("Literatura");
        System.out.println(livros);
    }
}