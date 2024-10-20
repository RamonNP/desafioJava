package br.com.biblioteca.core.gateway;

import br.com.biblioteca.core.model.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaGateway {

    Pessoa save (Pessoa pessoa);
    List<Pessoa> findAll ();
    Optional<Pessoa> findById(Long id);

    void deleteById(Long id);
    List<Pessoa> findGerentes();
}
