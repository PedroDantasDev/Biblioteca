package com.exemplo.biblioteca.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.service.LivroService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LivroController.class)
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LivroService livroService;

    @Autowired
    private ObjectMapper mapeador;

    private Livro testeLivro;

    @BeforeEach
    void setUp() {
        testeLivro = new Livro();
        testeLivro.setId(1L);
        testeLivro.setTitulo("A jornada de José");
        testeLivro.setAutor("José da Silva");
        testeLivro.setIsbn("1234567890");
        testeLivro.setDataPublicacao(LocalDate.of(2021, 1, 1));
        testeLivro.setCategoria("Ficção");
    }

    @Test
    void getTodosLivros() throws Exception {
        when(livroService.getAllLivros()).thenReturn(Arrays.asList(testeLivro));

        mockMvc.perform(get("/api/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testeLivro.getId()))
                .andExpect(jsonPath("$[0].titulo").value(testeLivro.getTitulo()))
                .andExpect(jsonPath("$[0].autor").value(testeLivro.getAutor()))
                .andExpect(jsonPath("$[0].isbn").value(testeLivro.getIsbn()))
                .andExpect(jsonPath("$[0].dataPublicacao").value("2021-01-01"))
                .andExpect(jsonPath("$[0].categoria").value(testeLivro.getCategoria()));
    }

    @Test
    void testGetLivroById() throws Exception {
        Long id = 1L;
        Livro livro = new Livro();
        livro.setId(id);
        livro.setTitulo("O Livro");
        livro.setAutor("José da Silva");
        
        when(livroService.getLivroById(id)).thenReturn(Optional.of(livro));
    
        mockMvc.perform(get("/api/livros/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.titulo").value("O Livro"))
                .andExpect(jsonPath("$.autor").value("José da Silva"));
    }

    @Test
    void createLivro() throws Exception {
        when(livroService.saveLivro(any(Livro.class))).thenReturn(testeLivro);

        mockMvc.perform(post("/api/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapeador.writeValueAsString(testeLivro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testeLivro.getId()))
                .andExpect(jsonPath("$.titulo").value(testeLivro.getTitulo()))
                .andExpect(jsonPath("$.autor").value(testeLivro.getAutor()))
                .andExpect(jsonPath("$.isbn").value(testeLivro.getIsbn()))
                .andExpect(jsonPath("$.dataPublicacao").value("2021-01-01"))
                .andExpect(jsonPath("$.categoria").value(testeLivro.getCategoria()));
    }

    @Test
    void updateLivro() throws Exception {
        when(livroService.updateLivro(eq(1L), any(Livro.class))).thenReturn(testeLivro);

        mockMvc.perform(put("/api/livros/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapeador.writeValueAsString(testeLivro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testeLivro.getId()))
                .andExpect(jsonPath("$.titulo").value(testeLivro.getTitulo()))
                .andExpect(jsonPath("$.autor").value(testeLivro.getAutor()))
                .andExpect(jsonPath("$.isbn").value(testeLivro.getIsbn()))
                .andExpect(jsonPath("$.dataPublicacao").value("2021-01-01"))
                .andExpect(jsonPath("$.categoria").value(testeLivro.getCategoria()));
    }

    @Test
    void deleteBook() throws Exception {
        mockMvc.perform(delete("/api/livros/1"))
                .andExpect(status().isOk());
    }
}