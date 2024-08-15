package com.exemplo.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.service.LivroService;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<Livro> getAllLivros() {
        return livroService.getAllLivros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> getLivroById(@PathVariable Long id) {
        return livroService.getLivroById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/recomendacoes/{usuarioId}")
    public ResponseEntity<List<Livro>> getRecomendacoes(@PathVariable Long usuarioId) {
        List<Livro> recomendacoes = livroService.getRecomendacoes(usuarioId);
        return ResponseEntity.ok(recomendacoes);
    }
    @PostMapping
    public ResponseEntity<Livro> createLivro(@RequestBody Livro livro) {
        System.out.println("Recebido livro para criar: " + livro);
        Livro savedLivro = livroService.saveLivro(livro);
        System.out.println("Livro salvo: " + savedLivro);
        return ResponseEntity.ok(savedLivro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> updateLivro(@PathVariable Long id, @RequestBody Livro livro) {
        System.out.println("Recebido livro para atualizar: " + livro);
        Livro updatedLivro = livroService.updateLivro(id, livro);
        System.out.println("Livro atualizado: " + updatedLivro);
        return ResponseEntity.ok(updatedLivro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        livroService.deleteLivro(id);
        return ResponseEntity.ok().build();
    }
}