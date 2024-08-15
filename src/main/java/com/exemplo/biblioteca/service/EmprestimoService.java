package com.exemplo.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemplo.biblioteca.model.Emprestimo;
import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.model.Usuario;
import com.exemplo.biblioteca.repository.EmprestimoRepository;
import com.exemplo.biblioteca.repository.LivroRepository;
import com.exemplo.biblioteca.repository.UsuarioRepository;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
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

    public Emprestimo createEmprestimo(Emprestimo emprestimo) {
        Livro livro = livroRepository.findById(emprestimo.getLivro().getId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        Usuario usuario = usuarioRepository.findById(emprestimo.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        
        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo updateEmprestimo(Long id, Emprestimo emprestimoAtualizado) {
        return emprestimoRepository.findById(id)
                .map(emprestimo -> {
                    emprestimo.setDataEmprestimo(emprestimoAtualizado.getDataEmprestimo());
                    emprestimo.setDataDevolucao(emprestimoAtualizado.getDataDevolucao());
                    emprestimo.setStatus(emprestimoAtualizado.getStatus());
                    
                    if (emprestimoAtualizado.getLivro() != null) {
                        Livro livro = livroRepository.findById(emprestimoAtualizado.getLivro().getId())
                                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
                        emprestimo.setLivro(livro);
                    }
                    
                    if (emprestimoAtualizado.getUsuario() != null) {
                        Usuario usuario = usuarioRepository.findById(emprestimoAtualizado.getUsuario().getId())
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                        emprestimo.setUsuario(usuario);
                    }
                    
                    return emprestimoRepository.save(emprestimo);
                })
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
    }

    public void deleteEmprestimo(Long id) {
        emprestimoRepository.deleteById(id);
    }
}