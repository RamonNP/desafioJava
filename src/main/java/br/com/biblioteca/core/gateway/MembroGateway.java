package br.com.biblioteca.core.gateway;

import br.com.biblioteca.core.model.Membro;

import java.util.List;
import java.util.Optional;

public interface MembroGateway {
    Membro save(Membro membro);
    List<Membro> findAll();
    Optional<Membro> findById(Long id);
    void deleteById(Long id);
    List<Membro> findByProjetoId(Long projetoId);
    void delete(Membro membro);
    Optional<Membro> findByProjetoIdAndPessoaId(Long projetoId, Long pessoaId);

}
