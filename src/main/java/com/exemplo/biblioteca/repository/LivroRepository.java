package com.exemplo.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemplo.biblioteca.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByCategoriasAndNotEmprestadoByUsuario(List<String> categoriasEmprestadas, Long usuarioId);
}
