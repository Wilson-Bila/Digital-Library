package com.example.biblioteca_digital.service;

import com.example.biblioteca_digital.model.Usuario;
import com.example.biblioteca_digital.repository.UsuarioRepository;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Wilson Bila");
        usuario.setEmail("bila@email.com");
        usuario.setSenha("senha123");
        usuario.setSaldo(100.0);
    }

    @Test
    void salvar_DeveRetornarUsuarioSalvo() {
        // Arrange
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        Usuario resultado = usuarioService.salvar(usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        assertEquals(usuario.getNome(), resultado.getNome());
        verify(usuarioRepository, times(1)).save(usuario);
        System.out.println(resultado);
    }

    @Test
    void listarTodos_DeveRetornarListaDeUsuarios() {
        // Arrange
        Usuario usuario2 = new Usuario(2L, "Cheila Souza", "cheila@email.com", "senha456", 200.0);
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario, usuario2));

        // Act
        List<Usuario> resultado = usuarioService.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
        System.out.println(resultado);
    }

    @Test
    void buscarPorId_QuandoUsuarioExiste_DeveRetornarUsuario() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Act
        Usuario resultado = usuarioService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        verify(usuarioRepository, times(1)).findById(1L);
        System.out.println(resultado);
    }

    @Test
    void buscarPorId_QuandoUsuarioNaoExiste_DeveLancarExcecao() {
        // Arrange
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioService.buscarPorId(99L)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(99L);
        System.out.println("Usuario nao encontrado!");
    }

    @Test
    void deletar_DeveChamarRepositorio() {
        // Arrange
        doNothing().when(usuarioRepository).deleteById(1L);

        // Act
        usuarioService.deletar(1L);

        // Assert
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void buscarPorEmail_QuandoUsuarioExiste_DeveRetornarUsuario() {
        // Arrange
        when(usuarioRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(usuario));

        // Act
        Usuario resultado = usuarioService.buscarPorEmail("joao@email.com");

        // Assert
        assertNotNull(resultado);
        assertEquals(usuario.getEmail(), resultado.getEmail());
        verify(usuarioRepository, times(1)).findByEmail("joao@email.com");
        System.out.println(resultado);
    }

    @Test
    void buscarPorEmail_QuandoUsuarioNaoExiste_DeveLancarExcecao() {
        // Arrange
        when(usuarioRepository.findByEmail("inexistente@email.com")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioService.buscarPorEmail("inexistente@email.com")
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioRepository, times(1)).findByEmail("inexistente@email.com");
        System.out.println("Usuario nao encontrado!");
    }
}