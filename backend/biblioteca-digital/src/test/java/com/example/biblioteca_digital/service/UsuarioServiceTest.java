package com.example.biblioteca_digital.service;

import com.example.biblioteca_digital.dto.UsuarioDTO;
import com.example.biblioteca_digital.model.Usuario;
import com.example.biblioteca_digital.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNomeCompleto("Ribeiro Moises");
        usuario.setEmail("rmoises@gmail.com");
        usuario.setSenha("senhaCR7123");

        usuarioDTO = new UsuarioDTO();
        usuario.setNomeCompleto("Ribeiro Moises");
        usuario.setEmail("rmoises@gmail.com");
        usuario.setSenha("senhaCR7123");
    }

    @Test
    @DisplayName("Deve retornar uma lista de usuários")
    void listarTodosUsuarios() {
        // Arrange
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario));

        // Act
        List<Usuario> result = usuarioService.listarTodos();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(usuario.getEmail(), result.get(0).getEmail());
        verify(usuarioRepository, times(1)).findAll();
        System.out.println(result);
        System.out.println("Teste para listar-Todos-Usuarios passou com sucesso.");
    }

    @Test
    @DisplayName("Deve retornar OK quando o usuário existe")
    void buscarPorIdUsuario() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Act
        Optional<Usuario> result = usuarioService.buscarPorId(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(usuario.getEmail(), result.get().getEmail());
        verify(usuarioRepository, times(1)).findById(1L);
        System.out.println("Teste buscarPorId_QuandoUsuarioExiste_DeveRetornarOk passou com sucesso.");
    }

    @Test
    @DisplayName("Deve retornar NOT FOUND quando o usuário não existe")
    void buscarPorIdRetornarVazio() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Usuario> result = usuarioService.buscarPorId(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(usuarioRepository, times(1)).findById(1L);
        System.out.println(result);
        System.out.println("Teste buscarPorId_RetornarNotFound passou com sucesso.");
    }

    @Test
    @DisplayName("Deve criar um usuário com dados válidos")
    void criar_ComDadosValidos_RetornarUsuarioCriado() {
        // Arrange
        when(usuarioRepository.existsByEmail(usuarioDTO.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        Usuario result = usuarioService.criar(usuarioDTO);

        // Assert
        assertNotNull(result);
        assertEquals(usuario.getEmail(), result.getEmail());
        verify(usuarioRepository, times(1)).existsByEmail(usuarioDTO.getEmail());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        System.out.println(result);
        System.out.println("Teste criar_ComDadosValidos_RetornarUsuarioCriado passou com sucesso.");
    }

    @Test
    @DisplayName("Deve retornar BAD REQUEST ao tentar criar com e-mail existente")
    void criar_ComEmailExistente_LancarExcecao() {
        // Arrange
        when(usuarioRepository.existsByEmail(usuarioDTO.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> usuarioService.criar(usuarioDTO));
        verify(usuarioRepository, times(1)).existsByEmail(usuarioDTO.getEmail());
        verify(usuarioRepository, never()).save(any(Usuario.class));
        System.out.println("Teste criar_ComEmailExistente_RetornarBadRequest passou com sucesso.");
    }

    @Test
    @DisplayName("Deve actualizar usuário existente")
    void actualizar_QuandoUsuarioExiste() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        usuarioDTO.setNomeCompleto("Wilson Bila Actualizado");

        // Act
        Optional<Usuario> result = usuarioService.actualizar(1L, usuarioDTO);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Wilson Bila Actualizado", result.get().getNomeCompleto());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        System.out.println(result);
        System.out.println("Teste actualizarQuandoUsuarioExiste passou com sucesso.");
    }

    @Test
    @DisplayName("Deve retornar NOT FOUND ao actualizar usuário inexistente")
    void actualizarUsuarioNaoExistenteRetornarVazio() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Usuario> result = usuarioService.actualizar(1L, usuarioDTO);

        // Assert
        assertFalse(result.isPresent());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, never()).save(any(Usuario.class));
        System.out.println(result);
        System.out.println("Teste actualizar_QuandoUsuarioNaoExiste_RetornarNotFound passou com sucesso.");
    }

    @Test
    @DisplayName("Deve remover usuário e retornar NO CONTENT")
    void remover() {
        // Arrange
        doNothing().when(usuarioRepository).deleteById(1L);

        // Act
        usuarioService.remover(1L);

        // Assert
        verify(usuarioRepository, times(1)).deleteById(1L);
        if(usuarioService.equals(true)){
            System.out.println("NO CONTENT");
        }
        System.out.println("Teste remover passou com sucesso.");
    }
}