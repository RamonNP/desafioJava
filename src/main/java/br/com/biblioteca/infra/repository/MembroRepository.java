package br.com.biblioteca.infra.repository;

import br.com.biblioteca.infra.database.jpa.MembroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembroRepository extends JpaRepository<MembroEntity, Long> {
    List<MembroEntity> findByProjetoId(Long projetoId);
    Optional<MembroEntity> findByProjetoIdAndPessoaId(Long projetoId, Long pessoaId);
}
