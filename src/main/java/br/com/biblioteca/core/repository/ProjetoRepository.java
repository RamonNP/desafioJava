package br.com.biblioteca.core.repository;

import br.com.biblioteca.core.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
