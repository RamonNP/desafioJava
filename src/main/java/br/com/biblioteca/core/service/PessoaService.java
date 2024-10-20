package br.com.biblioteca.core.service;

import br.com.biblioteca.core.gateway.PessoaGateway;
import br.com.biblioteca.core.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PessoaService {

    private final PessoaGateway pessoaGateway;

    public List<Pessoa> findAll() {
        try {
            log.info("Buscando todas as pessoas.");
            return pessoaGateway.findAll();
        } catch (Exception e) {
            log.error("Erro ao buscar todas as pessoas: {}", e.getMessage(), e);
            throw e; // Relança a exceção para tratamento em níveis superiores, se necessário
        }
    }

    public Pessoa save(Pessoa pessoa) {
        try {
            log.info("Iniciando o salvamento da pessoa: {}", pessoa.getNome());
            Pessoa savedPessoa = pessoaGateway.save(pessoa);
            log.info("Pessoa salva com sucesso: {}", savedPessoa);
            return savedPessoa;
        } catch (Exception e) {
            log.error("Erro ao salvar a pessoa: {} - {}", pessoa.getNome(), e.getMessage(), e);
            throw e; // Relança a exceção para tratamento em níveis superiores, se necessário
        }
    }

    public Pessoa findById(Long id) {
        try {
            log.info("Buscando pessoa com ID: {}", id);
            return pessoaGateway.findById(id).orElse(null);
        } catch (Exception e) {
            log.error("Erro ao buscar a pessoa com ID {}: {}", id, e.getMessage(), e);
            throw e; // Relança a exceção para tratamento em níveis superiores, se necessário
        }
    }

    public void deleteById(Long id) {
        try {
            log.info("Iniciando a exclusão da pessoa com ID: {}", id);
            pessoaGateway.deleteById(id);
            log.info("Pessoa com ID {} excluída com sucesso.", id);
        } catch (Exception e) {
            log.error("Erro ao excluir a pessoa com ID {}: {}", id, e.getMessage(), e);
            throw e; // Relança a exceção para tratamento em níveis superiores, se necessário
        }
    }

    public List<Pessoa> findGerentes() {
        try {
            log.info("Iniciando a busca por pessoas que são gerentes.");
            List<Pessoa> gerentes = pessoaGateway.findGerentes();
            log.info("Busca por gerentes concluída. Total de gerentes encontrados: {}", gerentes.size());
            return gerentes;
        } catch (Exception e) {
            log.error("Erro ao buscar gerentes: {}", e.getMessage(), e);
            throw e; // Relança a exceção para tratamento em níveis superiores, se necessário
        }
    }



}
