package com.example.biblioteca_digital.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.biblioteca_digital.dto.UsuarioDTO;
import com.example.biblioteca_digital.model.Usuario;
import com.example.biblioteca_digital.service.UsuarioService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNomeCompleto("Wilson Bila");
        usuario.setEmail("wilsonb@gmail.com");
        usuario.setSenha("senhaCR7123");

        usuarioDTO = new UsuarioDTO();
        usuario.setNomeCompleto("Wilson Bila");
        usuario.setEmail("wilsonb@gmail.com");
        usuario.setSenha("senhaCR7123");
    }

    @Test
    @DisplayName("Deve retornar uma lista de usuários")
    void listarTodosUsuarios() {
        // Arrange
        when(usuarioService.listarTodos()).thenReturn(Arrays.asList(usuario));

        // Act
        List<Usuario> result = usuarioController.listarTodos();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(usuario.getEmail(), result.get(0).getEmail());
        verify(usuarioService, times(1)).listarTodos();
        System.out.println(result);
        System.out.println("Teste para listar-Todos-Usuarios passou com sucesso.");
    }

    @Test
    @DisplayName("Deve retornar OK quando o usuário existe")
    void buscarPorId_QuandoUsuarioExiste_DeveRetornarOk() {
        // Arrange
        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        // Act
        ResponseEntity<Usuario> response = usuarioController.buscarPorId(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuario.getEmail(), response.getBody().getEmail());
        verify(usuarioService, times(1)).buscarPorId(1L);
        System.out.println(response);
        System.out.println("Teste buscarPorId_QuandoUsuarioExiste_RetornaOk passou com sucesso.");
    }

    @Test
    @DisplayName("Deve retornar NOT FOUND quando o usuário não existe")
    void buscarPorId_QuandoUsuarioNaoExiste_RetornarNotFound() {
        // Arrange
        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Usuario> response = usuarioController.buscarPorId(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(usuarioService, times(1)).buscarPorId(1L);
        System.out.println(response);
        System.out.println("Teste buscarPorId_RetornarNotFound passou com sucesso.");
    }

    @Test
    @DisplayName("Deve criar um usuário com dados válidos")
    void criar_ComDadosValidos_RetornarUsuarioCriado() {
        // Arrange
        when(usuarioService.criar(any(UsuarioDTO.class))).thenReturn(usuario);

        // Act
        ResponseEntity<?> response = usuarioController.criar(usuarioDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Usuario);
        verify(usuarioService, times(1)).criar(any(UsuarioDTO.class));
        System.out.println(response);
        System.out.println("Teste criar_ComDadosValidos_RetornarUsuarioCriado passou com sucesso.");
    }

    @Test
    @DisplayName("Deve retornar BAD REQUEST ao tentar criar com e-mail existente")
    void criar_ComEmailExistente_RetornarBadRequest() {
        // Arrange
        when(usuarioService.criar(any(UsuarioDTO.class)))
                .thenThrow(new RuntimeException("Email já está em uso"));

        // Act
        ResponseEntity<?> response = usuarioController.criar(usuarioDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email já está em uso", response.getBody());
        verify(usuarioService, times(1)).criar(any(UsuarioDTO.class));
        System.out.println(response);
        System.out.println("Teste criar_ComEmailExistente_RetornarBadRequest passou com sucesso.");
    }

    @Test
    @DisplayName("Deve actualizar usuário existente")
    void actualizar_QuandoUsuarioExiste_DeveRetornarUsuarioAtualizado() {
        // Arrange
        when(usuarioService.actualizar(anyLong(), any(UsuarioDTO.class)))
                .thenReturn(Optional.of(usuario));

        // Act
        ResponseEntity<Usuario> response = usuarioController.actualizar(1L, usuarioDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuario.getEmail(), response.getBody().getEmail());
        verify(usuarioService, times(1)).actualizar(anyLong(), any(UsuarioDTO.class));
        System.out.println("Teste actualizarQuandoUsuarioExiste passou com sucesso.");
    }

    @Test
    @DisplayName("Deve retornar NOT FOUND ao actualizar usuário inexistente")
    void actualizar_QuandoUsuarioNaoExiste_DeveRetornarNotFound() {
        // Arrange
        when(usuarioService.actualizar(anyLong(), any(UsuarioDTO.class)))
                .thenReturn(Optional.empty());

        // Act
        ResponseEntity<Usuario> response = usuarioController.actualizar(1L, usuarioDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(usuarioService, times(1)).actualizar(anyLong(), any(UsuarioDTO.class));
        System.out.println(response);
        System.out.println("Teste actualizar_QuandoUsuarioNaoExiste_RetornarNotFound passou com sucesso.");
    }

    @Test
    @DisplayName("Deve remover usuário e retornar NO CONTENT")
    void remover_DeveRetornarNoContent() {
        // Arrange
        doNothing().when(usuarioService).remover(anyLong());

        // Act
        ResponseEntity<Void> response = usuarioController.remover(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).remover(1L);
        System.out.println(response);
        System.out.println("Teste remover passou com sucesso.");
    }
}