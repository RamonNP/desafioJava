package br.com.biblioteca.core.repository;

import br.com.biblioteca.core.model.Membro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembroRepository extends JpaRepository<Membro, Long> {
}
