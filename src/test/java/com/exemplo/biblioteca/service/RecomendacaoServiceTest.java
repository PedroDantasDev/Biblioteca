package com.exemplo.biblioteca.service;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.exemplo.biblioteca.model.Emprestimo;
import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.model.Usuario;
import com.exemplo.biblioteca.repository.EmprestimoRepository;
import com.exemplo.biblioteca.repository.LivroRepository;

class RecomendacaoServiceTest {

    @InjectMocks
    private RecomendacaoService recomendacaoService;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private LivroRepository livroRepository;

    private Usuario testeUsuario;
    private Livro teste1, teste2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testeUsuario = new Usuario();
        testeUsuario.setId(1L);

        teste1 = new Livro();
        teste1.setId(1L);
        teste1.setCategoria("Ficção");

        teste2 = new Livro();
        teste2.setId(2L);
        teste2.setCategoria("Ficção");

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(testeUsuario);
        emprestimo.setLivro(teste1);

        when(emprestimoRepository.findByUsuario(testeUsuario)).thenReturn(Arrays.asList(emprestimo));
        when(livroRepository.findAll()).thenReturn(Arrays.asList(teste1, teste2));
        when(emprestimoRepository.existsByUsuarioAndLivro(testeUsuario, teste1)).thenReturn(true);
        when(emprestimoRepository.existsByUsuarioAndLivro(testeUsuario, teste2)).thenReturn(false);
    }

    @Test
    void getRecomendacaoPorUsuario() {
        List<Livro> recomendacoes = recomendacaoService.getRecomendacaoPorUsuario(testeUsuario);
        assertEquals(1, recomendacoes.size());
        assertEquals(teste2, recomendacoes.get(0));
    }
}