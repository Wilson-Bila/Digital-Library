package com.example.biblioteca_digital.controller;

import com.example.biblioteca_digital.model.Usuario;
import com.example.biblioteca_digital.service.UsuarioService;
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

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario(1L, "Wilson Bila", "bila@email.com", "senha123", 100.0);
    }

    @Test
    void criarUsuario() {
        when(usuarioService.salvar(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.criarUsuario(usuario);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Wilson Bila", response.getBody().getNome());
        verify(usuarioService, times(1)).salvar(usuario);
        System.out.println(response);
    }

    @Test
    void listarUsuarios() {
        when(usuarioService.listarTodos()).thenReturn(Arrays.asList(usuario));

        ResponseEntity<List<Usuario>> response = usuarioController.listarUsuarios();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        verify(usuarioService, times(1)).listarTodos();
        System.out.println(response);
    }

    @Test
    void buscarUsuarioPorId() {
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.buscarUsuarioPorId(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Wilson Bila", response.getBody().getNome());
        verify(usuarioService, times(1)).buscarPorId(1L);
        System.out.println(response);
    }

    @Test
    void deletarUsuario() {
        doNothing().when(usuarioService).deletar(1L);

        ResponseEntity<Void> response = usuarioController.deletarUsuario(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(usuarioService, times(1)).deletar(1L);
        System.out.println(response);
    }

    @Test
    void adicionarSaldo() {
        usuario.setSaldo(150.0);
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);
        when(usuarioService.salvar(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.adicionarSaldo(1L, 50.0);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(150.0, response.getBody().getSaldo());
        verify(usuarioService, times(1)).buscarPorId(1L);
        verify(usuarioService, times(1)).salvar(usuario);
        System.out.println(response);
    }
}