package com.example.biblioteca_digital.service;

import com.example.biblioteca_digital.dto.UsuarioDTO;
import com.example.biblioteca_digital.model.Usuario;
import com.example.biblioteca_digital.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario criar(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }

        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(usuarioDTO.getNomeCompleto());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> actualizar(Long id, UsuarioDTO usuarioDTO) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNomeCompleto(usuarioDTO.getNomeCompleto());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setSenha(usuarioDTO.getSenha());
            return usuarioRepository.save(usuario);
        });
    }

    public void remover(Long id) {
        usuarioRepository.deleteById(id);
    }
}