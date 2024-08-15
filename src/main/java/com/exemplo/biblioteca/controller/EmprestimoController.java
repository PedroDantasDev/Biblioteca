package com.exemplo.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.biblioteca.model.Emprestimo;
import com.exemplo.biblioteca.service.EmprestimoService;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {
    @Autowired
    private EmprestimoService emprestimoService;

    
    @GetMapping
    public ResponseEntity<List<Emprestimo>> getTodosEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoService.getTodosEmprestimos();
        System.out.println("Empréstimos enviados: " + emprestimos);
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> getEmprestimoPorId(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimoService.getEmprestimoPorId(id);
        return ResponseEntity.ok(emprestimo);
    }

    @PostMapping
    public ResponseEntity<?> createEmprestimo(@RequestBody Emprestimo emprestimo) {
        try {
            System.out.println("Recebido empréstimo para criar: " + emprestimo);
            Emprestimo novoEmprestimo = emprestimoService.createEmprestimo(emprestimo);
            System.out.println("Empréstimo criado com sucesso: " + novoEmprestimo);
            return ResponseEntity.ok(novoEmprestimo);
        } catch (Exception e) {
            System.err.println("Erro ao criar empréstimo: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao criar empréstimo: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emprestimo> updateEmprestimo(@PathVariable Long id, @RequestBody Emprestimo emprestimo) {
        Emprestimo updatedEmprestimo = emprestimoService.updateEmprestimo(id, emprestimo);
        return ResponseEntity.ok(updatedEmprestimo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmprestimo(@PathVariable Long id) {
        emprestimoService.deleteEmprestimo(id);
        return ResponseEntity.ok().build();
    }
}
