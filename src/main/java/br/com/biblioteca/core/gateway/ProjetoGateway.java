package br.com.biblioteca.core.gateway;

import br.com.biblioteca.core.model.Projeto;

import java.util.List;
import java.util.Optional;

public interface ProjetoGateway {

    Projeto save(Projeto projeto);
    List<Projeto> findAll();
    Optional<Projeto> findById(Long id);
    void deleteById(Long id);
    List<Projeto> findAllComFuncionarios();
}
