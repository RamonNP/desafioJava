package br.com.biblioteca.infra.repository;

import br.com.biblioteca.infra.database.jpa.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoaRepository extends JpaRepository<PessoaEntity, Long> {
    List<PessoaEntity> findAllByGerenteTrue();
}
