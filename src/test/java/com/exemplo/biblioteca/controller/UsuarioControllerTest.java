package com.exemplo.biblioteca.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.exemplo.biblioteca.model.Usuario;
import com.exemplo.biblioteca.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
@WebMvcTest(UsuarioController.class)

class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper mapeador;

    private Usuario testeUsuario;

    @BeforeEach
    void setUp() {
        testeUsuario = new Usuario();
        testeUsuario.setId(1L);
        testeUsuario.setNome("João Gomes");
        testeUsuario.setEmail("joaogomes@gmail.com");

    }

    @Test
    void getTodosUsuarios() throws Exception {
        when(usuarioService.getTodosUsuarios()).thenReturn(Arrays.asList(testeUsuario));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testeUsuario.getId()))
                .andExpect(jsonPath("$[0].nome").value(testeUsuario.getNome()))
                .andExpect(jsonPath("$[0].email").value(testeUsuario.getEmail()));
    }

    @Test
    public void getUsuarioPorId() throws Exception {
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setNome("Test User");
    
        when(usuarioService.getUsuarioPorId(userId)).thenReturn(usuario);
    
        mockMvc.perform(get("/api/usuarios/{id}", userId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(userId))
            .andExpect(jsonPath("$.nome").value("Test User"));
    }

    @Test
    void createUsuario() throws Exception {
        when(usuarioService.createUsuario(any(Usuario.class))).thenReturn(testeUsuario);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapeador.writeValueAsString(testeUsuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testeUsuario.getId()))
                .andExpect(jsonPath("$.nome").value(testeUsuario.getNome()))
                .andExpect(jsonPath("$.email").value(testeUsuario.getEmail()));
    }

    @Test
    void updateUsuario() throws Exception {
        when(usuarioService.updateUsuario(eq(1L), any(Usuario.class))).thenReturn(testeUsuario);

        mockMvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapeador.writeValueAsString(testeUsuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testeUsuario.getId()))
                .andExpect(jsonPath("$.nome").value(testeUsuario.getNome()))
                .andExpect(jsonPath("$.email").value(testeUsuario.getEmail()));
    }

    @Test
    void deleteUsuario() throws Exception {
        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isOk());
    }
}