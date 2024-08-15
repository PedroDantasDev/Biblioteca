package com.exemplo.biblioteca.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemplo.biblioteca.model.Emprestimo;
import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.model.Usuario;
import com.exemplo.biblioteca.repository.EmprestimoRepository;
import com.exemplo.biblioteca.repository.LivroRepository;

@Service
public class RecomendacaoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;


    public List<Livro> getRecomendacaoPorUsuario(Usuario usuario) {

        List<Emprestimo> usuarioEmprestimo = emprestimoRepository.findByUsuario(usuario);


        Set<String> userCategories = usuarioEmprestimo.stream()
                .map(emprestimo -> emprestimo.getLivro().getCategoria())
                .collect(Collectors.toSet());

        List<Livro> livros = livroRepository.findAll();

        List<Livro> livrosRecomendados = livros.stream()
                .filter(livro -> userCategories.contains(livro.getCategoria()))
                .filter(livro -> !usuarioPegouEmprestado(usuario, livro))
                .collect(Collectors.toList());

        livrosRecomendados.sort((l1, l2) -> {
            long l1Count = emprestimoRepository.countByLivro(l1);
            long l2Count = emprestimoRepository.countByLivro(l2);
            return Long.compare(l1Count, l2Count); 
        });

        return livrosRecomendados.stream()
                .limit(10)
                .collect(Collectors.toList());
    }

    private boolean usuarioPegouEmprestado(Usuario usuario, Livro livro) {
        return emprestimoRepository.existsByUsuarioAndLivro(usuario, livro);
    }
}
