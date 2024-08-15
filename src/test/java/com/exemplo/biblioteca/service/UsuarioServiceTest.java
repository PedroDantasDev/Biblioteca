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

import com.exemplo.biblioteca.model.Usuario;
import com.exemplo.biblioteca.repository.UsuarioRepository;

class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario testUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUsuario = new Usuario();
        testUsuario.setId(1L);
        testUsuario.setNome("João Gomes");
        testUsuario.setEmail("joaogomes@gmail.com");
        testUsuario.setDataCadastro(LocalDate.now());
        testUsuario.setTelefone("1234567890");
    }

    @Test
    void getTodosUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(testUsuario));
        List<Usuario> usuarios = usuarioService.getTodosUsuarios();
        assertEquals(1, usuarios.size());
        assertEquals(testUsuario, usuarios.get(0));
    }

    @Test
    void getUsuarioPorId() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(testUsuario));
        Usuario usuario = usuarioService.getUsuarioPorId(1L);
        assertEquals(testUsuario, usuario);
    }

    @Test
    void createUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(testUsuario);
        Usuario createdUsuario = usuarioService.createUsuario(testUsuario);
        assertEquals(testUsuario, createdUsuario);
    }

    @Test
    void updateUsuario() {
        Usuario updatedUsuario = new Usuario();
        updatedUsuario.setNome("Joel Carlos");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(testUsuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(updatedUsuario);
        Usuario result = usuarioService.updateUsuario(1L, updatedUsuario);
        assertEquals("Joel Carlos", result.getNome());
    }

    @Test
    void deleteUsuario() {
        usuarioService.deleteUsuario(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}