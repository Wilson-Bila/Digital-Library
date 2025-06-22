package com.example.biblioteca_digital.controller;

import com.example.biblioteca_digital.model.Usuario;
import com.example.biblioteca_digital.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AuthController authController;

    private Usuario usuarioValido;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioValido = new Usuario();
        usuarioValido.setId(1L);
        usuarioValido.setEmail("usuario@teste.com");
        usuarioValido.setSenha("senha123");
        usuarioValido.setNome("Usuário Teste");
        usuarioValido.setSaldo(100.0);
    }

    @Test
    void login_Sucesso() {
        // Arrange
        Usuario loginRequest = new Usuario();
        loginRequest.setEmail("usuario@teste.com");
        loginRequest.setSenha("senha123");

        when(usuarioService.buscarPorEmail(anyString())).thenReturn(usuarioValido);

        // Act
        ResponseEntity<Usuario> response = authController.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuarioValido, response.getBody());
        verify(usuarioService, times(1)).buscarPorEmail("usuario@teste.com");
        System.out.println(response);
    }

    @Test
    void login_UsuarioNaoEncontrado() {
        // Arrange
        Usuario loginRequest = new Usuario();
        loginRequest.setEmail("naoexiste@teste.com");
        loginRequest.setSenha("senha123");

        when(usuarioService.buscarPorEmail(anyString()))
                .thenThrow(new RuntimeException("Usuário não encontrado"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authController.login(loginRequest));
        verify(usuarioService, times(1)).buscarPorEmail("naoexiste@teste.com");
    }

    @Test
    void login_SenhaIncorreta() {
        // Arrange
        Usuario loginRequest = new Usuario();
        loginRequest.setEmail("usuario@teste.com");
        loginRequest.setSenha("senhaErrada"); // Senha incorreta

        when(usuarioService.buscarPorEmail(anyString())).thenReturn(usuarioValido);

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authController.login(loginRequest)
        );

        assertEquals("Senha incorreta", exception.getMessage());
        verify(usuarioService, times(1)).buscarPorEmail("usuario@teste.com");
        System.out.println("Senha incorreta!");
    }

    @Test
    void login_EmailNulo() {
        // Arrange
        Usuario loginRequest = new Usuario();
        loginRequest.setEmail(null); // Email nulo
        loginRequest.setSenha("senha123");

        // Act & Assert
        assertThrows(NullPointerException.class, () -> authController.login(loginRequest));
        verify(usuarioService, never()).buscarPorEmail(anyString());
    }

    @Test
    void login_SenhaNula() {
        // Arrange
        Usuario loginRequest = new Usuario();
        loginRequest.setEmail("usuario@teste.com");
        loginRequest.setSenha(null); // Senha nula

        when(usuarioService.buscarPorEmail(anyString())).thenReturn(usuarioValido);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> authController.login(loginRequest));
        verify(usuarioService, times(1)).buscarPorEmail("usuario@teste.com");
    }
}