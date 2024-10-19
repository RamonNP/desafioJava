package br.com.biblioteca.infra.repository;


import br.com.biblioteca.infra.database.jpa.ProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<ProjetoEntity, Long> {
}
