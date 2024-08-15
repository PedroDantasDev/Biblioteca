package com.exemplo.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemplo.biblioteca.model.Usuario;
import com.exemplo.biblioteca.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repositorio;

    public List<Usuario> getTodosUsuarios() {
        return repositorio.findAll();
    }

    public Usuario getUsuarioPorId(Long id) {
        return repositorio.findById(id).orElse(null);
    }

    public Usuario createUsuario(Usuario usuario) {
        return repositorio.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario detalhesUsuario) {
        Usuario usuario = getUsuarioPorId(id);
        if (usuario != null) {
            return repositorio.save(usuario);
        }
        return null;
    }

    public void deleteUsuario(Long id) {
        repositorio.deleteById(id);
    }
}
