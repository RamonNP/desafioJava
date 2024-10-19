package br.com.biblioteca.infra.dataprovider;

import br.com.biblioteca.core.gateway.PessoaGateway;
import br.com.biblioteca.core.model.Pessoa;
import br.com.biblioteca.infra.mapper.PessoaMapper;
import br.com.biblioteca.infra.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PessoaProvider implements PessoaGateway {

    private final PessoaRepository pessoaRepository;

    @Override
    public Pessoa save(Pessoa pessoa) {
        var pessoaEntity = pessoaRepository.save(PessoaMapper.INSTANCE.toEntity(pessoa));
        return PessoaMapper.INSTANCE.toDomain(pessoaEntity);
    }

    @Override
    public List<Pessoa> findAll() {
        var pessoas = pessoaRepository.findAll();
        if(pessoas.isEmpty()) {
            log.info("Lista vazia");
            return List.of();
        }
        return  pessoas.stream().map(PessoaMapper.INSTANCE::toDomain).toList();
    }
    @Override
    public Optional<Pessoa> findById(Long id) {
        var pessoaEntity = pessoaRepository.findById(id);
        if(pessoaEntity.isEmpty()) {
            log.info("Lista vazia");
            return Optional.empty();
        }
        return Optional.of(PessoaMapper.INSTANCE.toDomain(pessoaEntity.get()));
    }
}
