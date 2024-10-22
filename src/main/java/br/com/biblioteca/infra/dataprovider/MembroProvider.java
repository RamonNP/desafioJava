package br.com.biblioteca.infra.dataprovider;

import br.com.biblioteca.core.gateway.MembroGateway;
import br.com.biblioteca.core.model.Membro;
import br.com.biblioteca.infra.database.jpa.MembroEntity;
import br.com.biblioteca.infra.repository.MembroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class MembroProvider implements MembroGateway {

    private final MembroRepository membroRepository;
    private final ModelMapper modelMapper;

    @Override
    public Membro save(Membro membro) {
        try {
            var membroEntity = membroRepository.save(modelMapper.map(membro, MembroEntity.class));
            return modelMapper.map(membroEntity, Membro.class);
        } catch (Exception e) {
            log.error("Erro ao salvar o membro: {} - {}", membro.getPessoa().getNome(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Membro> findAll() {
        try {
            var membros = membroRepository.findAll();
            if (membros.isEmpty()) {
                log.info("Lista de membros vazia.");
                return List.of();
            }
            log.info("Total de membros encontrados: {}", membros.size());
            return membros.stream()
                    .map(membroEntity -> modelMapper.map(membroEntity, Membro.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao buscar todos os membros: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Membro> findById(Long id) {
        try {
            var membroEntity = membroRepository.findById(id);
            if (membroEntity.isEmpty()) {
                log.info("Membro com ID {} não encontrado.", id);
                return Optional.empty();
            }
            log.info("Membro encontrado: {}", membroEntity.get().getPessoa().getNome());
            return Optional.of(modelMapper.map(membroEntity.get(), Membro.class));
        } catch (Exception e) {
            log.error("Erro ao buscar o membro com ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            log.info("Iniciando a exclusão do membro com ID: {}", id);
            membroRepository.deleteById(id);
            log.info("Membro com ID {} excluído com sucesso.", id);
        } catch (Exception e) {
            log.error("Erro ao excluir o membro com ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Membro> findByProjetoId(Long projetoId) {
        try {
            var membros = membroRepository.findByProjetoId(projetoId);
            log.info("Total de membros encontrados para o projeto ID {}: {}", projetoId, membros.size());
            return membros.stream()
                    .map(membroEntity -> modelMapper.map(membroEntity, Membro.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao buscar membros do projeto com ID {}: {}", projetoId, e.getMessage(), e);
            throw e;
        }
    }
    @Override
    public Optional<Membro> findByProjetoIdAndPessoaId(Long projetoId, Long pessoaId) {
        try {
            var membroEntity = membroRepository.findByProjetoIdAndPessoaId(projetoId, pessoaId);
            if (membroEntity.isEmpty()) {
                log.info("Membro não encontrado para o projeto ID {} e pessoa ID {}.", projetoId, pessoaId);
                return Optional.empty();
            }
            log.info("Membro encontrado para o projeto ID {} e pessoa ID {}.", projetoId, pessoaId);
            return Optional.of(modelMapper.map(membroEntity.get(), Membro.class));
        } catch (Exception e) {
            log.error("Erro ao buscar membro para o projeto ID {} e pessoa ID {}: {}", projetoId, pessoaId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void delete(Membro membro) {
        try {
            log.info("Iniciando a exclusão do membro: {}", membro.getPessoa().getNome());
            membroRepository.delete(modelMapper.map(membro, MembroEntity.class));
            log.info("Membro {} excluído com sucesso.", membro.getPessoa().getNome());
        } catch (Exception e) {
            log.error("Erro ao excluir o membro: {} - {}", membro.getPessoa().getNome(), e.getMessage(), e);
            throw e;
        }
    }
}
