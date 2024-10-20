package br.com.biblioteca.infra.dataprovider;

import br.com.biblioteca.core.gateway.ProjetoGateway;
import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.infra.mapper.ProjetoMapper;
import br.com.biblioteca.infra.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjetoProvider implements ProjetoGateway {

    private final ProjetoRepository projetoRepository;

    @Override
    public Projeto save(Projeto projeto) {
        log.info("Iniciando o processo de salvamento do projeto: {}", projeto);

        try {
            var projetoEntity = projetoRepository.save(ProjetoMapper.INSTANCE.toEntity(projeto));
            log.info("Projeto salvo com sucesso no banco de dados: {}", projetoEntity);
            return ProjetoMapper.INSTANCE.toDomain(projetoEntity);
        } catch (Exception e) {
            log.error("Erro ao salvar o projeto: {} - Detalhes: {}", projeto, e.getMessage(), e);
            throw e; // Relança a exceção para que o controle possa lidar com ela em outros níveis, se necessário
        }
    }

    @Override
    public List<Projeto> findAll() {
        var projetos = projetoRepository.findAll();
        if(projetos.isEmpty()) {
            log.info("Lista de projetos vazia");
            return List.of();
        }
        return projetos.stream().map(ProjetoMapper.INSTANCE::toDomain).toList();
    }

    @Override
    public Optional<Projeto> findById(Long id) {
        var projetoEntity = projetoRepository.findById(id);
        if(projetoEntity.isEmpty()) {
            log.info("Projeto não encontrado para o ID: " + id);
            return Optional.empty();
        }
        return Optional.of(ProjetoMapper.INSTANCE.toDomain(projetoEntity.get()));
    }

    @Override
    public void deleteById(Long id) {
        projetoRepository.deleteById(id);
    }
}
