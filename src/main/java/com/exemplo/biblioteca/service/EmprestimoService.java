package com.exemplo.biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.biblioteca.model.Emprestimo;
import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.model.Usuario;
import com.exemplo.biblioteca.repository.EmprestimoRepository;
import com.exemplo.biblioteca.repository.LivroRepository;
import com.exemplo.biblioteca.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                            LivroRepository livroRepository,
                            UsuarioRepository usuarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Emprestimo> getTodosEmprestimos() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo getEmprestimoPorId(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
    }

    @Transactional
    public Emprestimo createEmprestimo(Emprestimo emprestimo) {
        System.out.println("Criando empréstimo: " + emprestimo);
        try {
            Livro livro = livroRepository.findById(emprestimo.getLivro().getId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com id " + emprestimo.getLivro().getId()));
            System.out.println("Livro encontrado: " + livro);
    
            Usuario usuario = usuarioRepository.findById(emprestimo.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id " + emprestimo.getUsuario().getId()));
            System.out.println("Usuário encontrado: " + usuario);
    
            emprestimo.setLivro(livro);
            emprestimo.setUsuario(usuario);
    
            Emprestimo savedEmprestimo = emprestimoRepository.save(emprestimo);
            System.out.println("Empréstimo salvo com sucesso: " + savedEmprestimo);
            return savedEmprestimo;
        } catch (Exception e) {
            System.err.println("Erro ao salvar empréstimo: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar empréstimo: " + e.getMessage(), e);
        }
    }

    public Emprestimo updateEmprestimo(Long id, Emprestimo emprestimo) {
        return emprestimoRepository.findById(id)
            .map(existingEmprestimo -> {
                if (emprestimo.getUsuario() != null) {
                    existingEmprestimo.setUsuario(emprestimo.getUsuario());
                }
                if (emprestimo.getLivro() != null) {
                    existingEmprestimo.setLivro(emprestimo.getLivro());
                }
                if (emprestimo.getDataEmprestimo() != null) {
                    existingEmprestimo.setDataEmprestimo(emprestimo.getDataEmprestimo());
                }
                if (emprestimo.getDataDevolucao() != null) {
                    existingEmprestimo.setDataDevolucao(emprestimo.getDataDevolucao());
                }
                if (emprestimo.getStatus() != null) {
                    existingEmprestimo.setStatus(emprestimo.getStatus());
                }
                return emprestimoRepository.save(existingEmprestimo);
            })
            .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
    }

    public void deleteEmprestimo(Long id) {
        emprestimoRepository.deleteById(id);
    }
}