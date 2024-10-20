package br.com.biblioteca.core.controller;

import br.com.biblioteca.core.model.Pessoa;
import br.com.biblioteca.core.service.PessoaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<Pessoa> createPessoa(@RequestBody Pessoa pessoa) {
        try {
            log.info("Iniciando o cadastro de uma nova pessoa: {}", pessoa.getNome());
            Pessoa savedPessoa = pessoaService.save(pessoa);
            log.info("Cadastro de pessoa realizado com sucesso: {}", savedPessoa);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPessoa);
        } catch (Exception e) {
            log.error("Erro ao cadastrar nova pessoa: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> listarPessoas() {
        try {
            log.info("Listando todas as pessoas.");
            List<Pessoa> pessoas = pessoaService.findAll();
            return ResponseEntity.ok(pessoas);
        } catch (Exception e) {
            log.error("Erro ao listar pessoas: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> alterarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        try {
            log.info("Iniciando a alteração da pessoa com ID: {}", id);
            pessoa.setId(id);
            Pessoa pessoaAlterada = pessoaService.save(pessoa);
            log.info("Pessoa com ID {} alterada com sucesso: {}", id, pessoaAlterada);
            return ResponseEntity.ok(pessoaAlterada);
        } catch (Exception e) {
            log.error("Erro ao alterar a pessoa com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPessoa(@PathVariable Long id) {
        try {
            log.info("Iniciando a exclusão da pessoa com ID: {}", id);
            pessoaService.deleteById(id);
            log.info("Pessoa com ID {} excluída com sucesso.", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao excluir a pessoa com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
