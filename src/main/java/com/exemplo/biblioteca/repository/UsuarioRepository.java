package com.exemplo.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemplo.biblioteca.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
