package com.exemplo.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemplo.biblioteca.model.Emprestimo;
import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.model.Usuario;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByUsuario(Usuario usuario);
    long countByLivro(Livro livro);
    boolean existsByUsuarioAndLivro(Usuario usuario, Livro livro);
    List<String> findCategoriasByUsuarioId(Long usuarioId);
}
