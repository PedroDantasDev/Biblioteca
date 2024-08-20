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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exemplo.biblioteca.model.Emprestimo;
import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.model.Usuario;
import com.exemplo.biblioteca.repository.EmprestimoRepository;
import com.exemplo.biblioteca.repository.LivroRepository;
import com.exemplo.biblioteca.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private EmprestimoService emprestimoService;

    private Emprestimo testeEmprestimo;
    private Usuario testeUsuario;
    private Livro testeLivro;

    @BeforeEach
    void setUp() {
        testeUsuario = new Usuario();
        testeUsuario.setId(1L);
        testeUsuario.setNome("João Gomes");
    
        testeLivro = new Livro();
        testeLivro.setId(1L);
        testeLivro.setTitulo("Teste Livro");
    
        testeEmprestimo = new Emprestimo(testeUsuario, testeLivro, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 15), "ATIVO");
        testeEmprestimo.setId(1L);
    }

    @Test
    void getTodosEmprestimos() {
        when(emprestimoRepository.findAll()).thenReturn(Arrays.asList(testeEmprestimo));
        List<Emprestimo> emprestimos = emprestimoService.getTodosEmprestimos();
        assertEquals(1, emprestimos.size());
        assertEquals(testeEmprestimo, emprestimos.get(0));
    }

    @Test
    void getEmprestimoPorId() {
        when(emprestimoRepository.findById(1L)).thenReturn(Optional.of(testeEmprestimo));
        Emprestimo emprestimo = emprestimoService.getEmprestimoPorId(1L);
        assertEquals(testeEmprestimo, emprestimo);
    }

    @Test
    void createEmprestimo() {
        when(livroRepository.findById(testeLivro.getId())).thenReturn(Optional.of(testeLivro));
        when(usuarioRepository.findById(testeUsuario.getId())).thenReturn(Optional.of(testeUsuario));
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(testeEmprestimo);

        Emprestimo createdEmprestimo = emprestimoService.createEmprestimo(testeEmprestimo);

        assertEquals(testeEmprestimo, createdEmprestimo);
        verify(livroRepository).findById(testeLivro.getId());
        verify(usuarioRepository).findById(testeUsuario.getId());
        verify(emprestimoRepository).save(any(Emprestimo.class));
    }

    @Test
    void updateEmprestimo() {
        Emprestimo updatedEmprestimo = new Emprestimo(testeUsuario, testeLivro, LocalDate.now(), LocalDate.now().plusDays(14), "DEVOLVIDO");
        
        when(emprestimoRepository.findById(1L)).thenReturn(Optional.of(testeEmprestimo));
        
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(updatedEmprestimo);
        
        Emprestimo result = emprestimoService.updateEmprestimo(1L, updatedEmprestimo);
        
        assertEquals("DEVOLVIDO", result.getStatus());
        assertEquals(updatedEmprestimo.getUsuario(), result.getUsuario());
        assertEquals(updatedEmprestimo.getLivro(), result.getLivro());
        assertEquals(updatedEmprestimo.getDataEmprestimo(), result.getDataEmprestimo());
        assertEquals(updatedEmprestimo.getDataDevolucao(), result.getDataDevolucao());

        verify(emprestimoRepository).findById(1L);
        verify(emprestimoRepository).save(any(Emprestimo.class));
    }

    @Test
    void deleteEmprestimo() {
        emprestimoService.deleteEmprestimo(1L);
        verify(emprestimoRepository, times(1)).deleteById(1L);
    }
}