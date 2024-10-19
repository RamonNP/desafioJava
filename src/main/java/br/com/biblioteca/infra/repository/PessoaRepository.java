package br.com.biblioteca.infra.repository;

import br.com.biblioteca.infra.database.jpa.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<PessoaEntity, Long> {
}
