package br.com.biblioteca.core.repository;

import br.com.biblioteca.core.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
