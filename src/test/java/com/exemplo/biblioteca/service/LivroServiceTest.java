package com.exemplo.biblioteca.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.repository.LivroRepository;

class LivroServiceTest {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    private Livro testelivro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testelivro = new Livro();
        testelivro.setId(1L);
        testelivro.setTitulo("Livro de Terror");
        testelivro.setAutor("Pedro Dantas");
        testelivro.setIsbn("1234567890");
        testelivro.setDataPublicacao(LocalDate.now());
        testelivro.setCategoria("Terror");
    }

    @Test
    void getTodosLivros() {
        when(livroRepository.findAll()).thenReturn(Arrays.asList(testelivro));
        List<Livro> livros = livroService.getAllLivros();
        assertEquals(1, livros.size());
        assertEquals(testelivro, livros.get(0));
    }

    @Test
    void getLivroPorId() {
        Long id = 1L;
        Livro livro = new Livro();
        livro.setId(id);
        when(livroRepository.findById(id)).thenReturn(Optional.of(livro));
    
        Optional<Livro> result = livroService.getLivroById(id);
    
        assertTrue(result.isPresent());
        assertEquals(livro, result.get());
    }

    @Test
    void createLivro() {
        when(livroRepository.save(any(Livro.class))).thenReturn(testelivro);
        Livro createLivro = livroService.saveLivro(testelivro);
        assertEquals(testelivro, createLivro);
    }

    @Test
    void updateLivro() {
        Livro updateLivro = new Livro();
        updateLivro.setTitulo("Livro dos Medos");
        when(livroRepository.findById(1L)).thenReturn(Optional.of(testelivro));
        when(livroRepository.save(any(Livro.class))).thenReturn(updateLivro);
        Livro result = livroService.updateLivro(1L, updateLivro);
        assertEquals("Livro dos Medos", result.getTitulo());
    }

    @Test
    void deleteLivro() {
        livroService.deleteLivro(1L);
        verify(livroRepository, times(1)).deleteById(1L);
    }
}