package br.com.biblioteca.infra.dataprovider;

import br.com.biblioteca.core.gateway.PessoaGateway;
import br.com.biblioteca.core.model.Pessoa;
import br.com.biblioteca.infra.database.jpa.PessoaEntity;
import br.com.biblioteca.infra.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PessoaProvider implements PessoaGateway {

    private final PessoaRepository pessoaRepository;
    private final ModelMapper modelMapper;

    @Override
    public Pessoa save(Pessoa pessoa) {
        try {
            log.info("Iniciando o salvamento da pessoa: {}", pessoa.getNome());
            var pessoaEntity = pessoaRepository.save(modelMapper.map(pessoa, PessoaEntity.class));
            log.info("Pessoa salva com sucesso: {}", pessoa.getNome());
            return modelMapper.map(pessoaEntity, Pessoa.class);
        } catch (Exception e) {
            log.error("Erro ao salvar a pessoa: {} - {}", pessoa.getNome(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Pessoa> findAll() {
        try {
            var pessoas = pessoaRepository.findAll();
            if (pessoas.isEmpty()) {
                log.info("Lista de pessoas vazia.");
                return List.of();
            }
            log.info("Total de pessoas encontradas: {}", pessoas.size());
            return pessoas.stream().map(pessoaEntity -> modelMapper.map(pessoaEntity, Pessoa.class)).toList();
        } catch (Exception e) {
            log.error("Erro ao buscar todas as pessoas: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Pessoa> findById(Long id) {
        try {
            var pessoaEntity = pessoaRepository.findById(id);
            if (pessoaEntity.isEmpty()) {
                log.info("Pessoa com ID {} não encontrada.", id);
                return Optional.empty();
            }
            log.info("Pessoa encontrada: {}", pessoaEntity.get().getNome());
            return Optional.of(modelMapper.map(pessoaEntity.get(), Pessoa.class));
        } catch (Exception e) {
            log.error("Erro ao buscar a pessoa com ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            log.info("Iniciando a exclusão da pessoa com ID: {}", id);
            pessoaRepository.deleteById(id);
            log.info("Pessoa com ID {} excluída com sucesso.", id);
        } catch (Exception e) {
            log.error("Erro ao excluir a pessoa com ID {}: {}", id, e.getMessage(), e);
            throw e; // Relança a exceção para tratamento em níveis superiores, se necessário
        }
    }

    @Override
    public List<Pessoa> findGerentes() {
        try {
            log.info("Buscando todas as pessoas que são gerentes.");
            var pessoas = pessoaRepository.findAllByGerenteTrue(); // Método que deve ser implementado no repositório
            return pessoas.stream()
                    .map(pessoaEntity -> modelMapper.map(pessoaEntity, Pessoa.class))
                    .toList();
        } catch (Exception e) {
            log.error("Erro ao buscar gerentes: {}", e.getMessage(), e);
            throw e; // Relança a exceção para tratamento em níveis superiores
        }
    }

    @Override
    public List<Pessoa> findAllFuncionarios() {
        try {
            log.info("Buscando todas as pessoas que são funcionários.");
            var funcionarios = pessoaRepository.findAllByFuncionarioTrue(); // Certifique-se de implementar esse método no repositório
            return funcionarios.stream()
                    .map(pessoaEntity -> modelMapper.map(pessoaEntity, Pessoa.class))
                    .toList();
        } catch (Exception e) {
            log.error("Erro ao buscar funcionários: {}", e.getMessage(), e);
            throw e;
        }
    }

}
