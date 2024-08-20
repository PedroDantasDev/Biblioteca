package com.exemplo.biblioteca.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.exemplo.biblioteca.model.Emprestimo;
import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.model.Usuario;
import com.exemplo.biblioteca.service.EmprestimoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EmprestimoController.class)
class EmprestimoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmprestimoService emprestimoService;

    @Autowired
    private ObjectMapper mapeador;

    private Emprestimo testeEmprestimo;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Gomes");

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Teste Livro");

        testeEmprestimo = new Emprestimo(usuario, livro, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 15), "ATIVO");
        testeEmprestimo.setId(1L);
        testeEmprestimo.setUsuario(usuario);
        testeEmprestimo.setLivro(livro);
        testeEmprestimo.setDataEmprestimo(LocalDate.of(2023, 1, 1));
        testeEmprestimo.setDataDevolucao(LocalDate.of(2023, 1, 15));
        testeEmprestimo.setStatus("ATIVO");
    }

    @Test
    void getTodosEmprestimos() throws Exception {
        when(emprestimoService.getTodosEmprestimos()).thenReturn(Arrays.asList(testeEmprestimo));

        mockMvc.perform(get("/api/emprestimos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testeEmprestimo.getId()))
                .andExpect(jsonPath("$[0].usuario.id").value(testeEmprestimo.getUsuario().getId()))
                .andExpect(jsonPath("$[0].livro.id").value(testeEmprestimo.getLivro().getId()))
                .andExpect(jsonPath("$[0].dataEmprestimo").value("2023-01-01"))
                .andExpect(jsonPath("$[0].dataDevolucao").value("2023-01-15"))
                .andExpect(jsonPath("$[0].status").value(testeEmprestimo.getStatus()));
    }

    @Test
    void getEmprestimoPorId() throws Exception {
        when(emprestimoService.getEmprestimoPorId(1L)).thenReturn(testeEmprestimo);

        mockMvc.perform(get("/api/emprestimos/{id}", 1L))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(testeEmprestimo.getId()))
               .andExpect(jsonPath("$.usuario.id").value(testeEmprestimo.getUsuario().getId()))
               .andExpect(jsonPath("$.livro.id").value(testeEmprestimo.getLivro().getId()))
               .andExpect(jsonPath("$.dataEmprestimo").value("2023-01-01"))
               .andExpect(jsonPath("$.dataDevolucao").value("2023-01-15"))
               .andExpect(jsonPath("$.status").value(testeEmprestimo.getStatus()));
    }

    @Test
    void createEmprestimo() throws Exception {
        when(emprestimoService.createEmprestimo(any(Emprestimo.class))).thenReturn(testeEmprestimo);

        MvcResult result = mockMvc.perform(post("/api/emprestimos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapeador.writeValueAsString(testeEmprestimo)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response content: " + content);

        mockMvc.perform(post("/api/emprestimos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapeador.writeValueAsString(testeEmprestimo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testeEmprestimo.getId()))
                .andExpect(jsonPath("$.usuario.id").value(testeEmprestimo.getUsuario().getId()))
                .andExpect(jsonPath("$.livro.id").value(testeEmprestimo.getLivro().getId()))
                .andExpect(jsonPath("$.dataEmprestimo").value("2023-01-01"))
                .andExpect(jsonPath("$.dataDevolucao").value("2023-01-15"))
                .andExpect(jsonPath("$.status").value(testeEmprestimo.getStatus()));
    }

    @Test
    void updateEmprestimo() throws Exception {
        when(emprestimoService.updateEmprestimo(eq(1L), any(Emprestimo.class))).thenReturn(testeEmprestimo);

        mockMvc.perform(put("/api/emprestimos/{id}", 1L)
               .contentType(MediaType.APPLICATION_JSON)
               .content(mapeador.writeValueAsString(testeEmprestimo)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(testeEmprestimo.getId()))
               .andExpect(jsonPath("$.usuario.id").value(testeEmprestimo.getUsuario().getId()))
               .andExpect(jsonPath("$.livro.id").value(testeEmprestimo.getLivro().getId()))
               .andExpect(jsonPath("$.dataEmprestimo").value("2023-01-01"))
               .andExpect(jsonPath("$.dataDevolucao").value("2023-01-15"))
               .andExpect(jsonPath("$.status").value(testeEmprestimo.getStatus()));
    }

    @Test
    void deleteEmprestimo() throws Exception {
        doNothing().when(emprestimoService).deleteEmprestimo(1L);

        mockMvc.perform(delete("/api/emprestimos/{id}", 1L))
               .andExpect(status().isOk());

        verify(emprestimoService, times(1)).deleteEmprestimo(1L);
    }
}