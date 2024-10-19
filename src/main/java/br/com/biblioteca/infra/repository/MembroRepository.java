package br.com.biblioteca.infra.repository;


import br.com.biblioteca.infra.database.jpa.MembroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembroRepository extends JpaRepository<MembroEntity, Long> {
}
