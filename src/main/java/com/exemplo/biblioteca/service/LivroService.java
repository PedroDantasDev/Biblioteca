package com.exemplo.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.repository.EmprestimoRepository;
import com.exemplo.biblioteca.repository.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> getAllLivros() {
        return livroRepository.findAll();
    }

    public Optional<Livro> getLivroById(Long id) {
        return livroRepository.findById(id);
    }

    public List<Livro> getRecomendacoes(Long usuarioId) {
        // Buscar as categorias dos livros que o usuário já pegou emprestado
        List<String> categoriasEmprestadas = emprestimoRepository.findCategoriasByUsuarioId(usuarioId);
        
        // Buscar livros das mesmas categorias que o usuário ainda não pegou emprestado
        return livroRepository.findByCategoriasAndNotEmprestadoByUsuario(categoriasEmprestadas, usuarioId);
    }
    
    public Livro saveLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    public Livro updateLivro(Long id, Livro livroDetails) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com id " + id));

        livro.setTitulo(livroDetails.getTitulo());
        livro.setAutor(livroDetails.getAutor());
        livro.setIsbn(livroDetails.getIsbn());
        livro.setDataPublicacao(livroDetails.getDataPublicacao());
        livro.setCategoria(livroDetails.getCategoria());

        return livroRepository.save(livro);
    }

    public void deleteLivro(Long id) {
        livroRepository.deleteById(id);
    }
}