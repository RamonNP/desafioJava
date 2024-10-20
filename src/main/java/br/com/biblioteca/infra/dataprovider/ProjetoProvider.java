package br.com.biblioteca.infra.dataprovider;

import br.com.biblioteca.core.gateway.ProjetoGateway;
import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.infra.database.jpa.ProjetoEntity;
import br.com.biblioteca.infra.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjetoProvider implements ProjetoGateway {

    private final ProjetoRepository projetoRepository;
    private final ModelMapper modelMapper;

    @Override
    public Projeto save(Projeto projeto) {
        log.info("Iniciando o processo de salvamento do projeto: {}", projeto);
        try {
            var projetoEntity = projetoRepository.save(modelMapper.map(projeto, ProjetoEntity.class));
            log.info("Projeto salvo com sucesso no banco de dados: {}", projetoEntity);
            return modelMapper.map(projetoEntity, Projeto.class);
        } catch (Exception e) {
            log.error("Erro ao salvar o projeto: {} - Detalhes: {}", projeto, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Projeto> findAll() {
        try {
            var projetos = projetoRepository.findAll();
            if (projetos.isEmpty()) {
                log.info("Lista de projetos vazia");
                return List.of();
            }
            return projetos.stream().map(projetoEntity -> modelMapper.map(projetoEntity, Projeto.class)).toList();
        } catch (Exception e) {
            log.error("Erro ao buscar todos os projetos: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Projeto> findById(Long id) {
        try {
            var projetoEntity = projetoRepository.findById(id);
            if (projetoEntity.isEmpty()) {
                log.info("Projeto não encontrado para o ID: {}", id);
                return Optional.empty();
            }
            return Optional.of(modelMapper.map(projetoEntity.get(), Projeto.class));
        } catch (Exception e) {
            log.error("Erro ao buscar o projeto com ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            projetoRepository.deleteById(id);
            log.info("Projeto com ID {} deletado com sucesso.", id);
        } catch (Exception e) {
            log.error("Erro ao deletar o projeto com ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
