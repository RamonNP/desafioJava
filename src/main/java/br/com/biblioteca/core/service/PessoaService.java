package br.com.biblioteca.core.service;

import br.com.biblioteca.core.gateway.PessoaGateway;
import br.com.biblioteca.core.model.Pessoa;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PessoaService {

    private final PessoaGateway pessoaGateway;

    public List<Pessoa> findAll() {
        return pessoaGateway.findAll();
    }

    public Pessoa save(Pessoa pessoa) {
        return pessoaGateway.save(pessoa);
    }

    public Pessoa findById(Long id) {
        return pessoaGateway.findById(id).orElse(null);
    }
}