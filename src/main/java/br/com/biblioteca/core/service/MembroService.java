package br.com.biblioteca.core.service;

import br.com.biblioteca.core.gateway.MembroGateway;
import br.com.biblioteca.core.gateway.PessoaGateway;
import br.com.biblioteca.core.model.Membro;
import br.com.biblioteca.core.model.Pessoa;
import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.cross.exceptions.MembroException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class MembroService {

    private final MembroGateway membroGateway;
    private final PessoaGateway pessoaGateway;
    private final ProjetoService projetoService;

    public List<Pessoa> findAllFuncionarios() throws RuntimeException {
        try {
            return pessoaGateway.findAllFuncionarios();
        } catch (Exception e) {
            log.error("Erro ao buscar lista de funcionários", e);
            throw new MembroException("Erro ao buscar lista de funcionários", e);
        }
    }

    public List<Membro> findAll() {
        try {
            return membroGateway.findAll();
        } catch (Exception e) {
            log.error("Erro ao buscar lista de membros", e);
            throw new MembroException("Erro ao buscar lista de membros", e);
        }
    }

    public Membro save(Membro membro) {
        try {
            return membroGateway.save(membro);
        } catch (Exception e) {
            log.error("Erro ao salvar membro", e);
            throw new MembroException("Erro ao salvar membro", e);
        }
    }

    public void associarFuncionariosAoProjeto(Long projetoId, List<Long> funcionarioIds) {
        try {
            Optional<Projeto> projetoOptional = projetoService.findById(projetoId);
            if (projetoOptional.isPresent()) {
                Projeto projeto = projetoOptional.get();
                for (Long funcionarioId : funcionarioIds) {
                    create(projetoId, funcionarioId, projeto);
                }
            } else {
                log.error("Projeto não encontrado com o ID: {}", projetoId);
                throw new IllegalArgumentException("Projeto não encontrado com o ID: " + projetoId);
            }
        } catch (Exception e) {
            log.error("Erro ao associar funcionários ao projeto com ID: {}", projetoId, e);
            throw new MembroException("Erro ao associar funcionários ao projeto", e);
        }
    }

    private void create(Long projetoId, Long funcionarioId, Projeto projeto) {
        try {
            Optional<Pessoa> funcionario = pessoaGateway.findById(funcionarioId);
            if (funcionario.isPresent()) {
                Membro membro = new Membro();
                membro.setProjeto(projeto);
                membro.setPessoa(funcionario.get());
                save(membro);
            } else {
                log.warn("Funcionário não encontrado com o ID: {}", funcionarioId);
            }
        } catch (Exception e) {
            log.error("Erro ao associar funcionário com ID: {} ao projeto: {}", funcionarioId, projetoId, e);
        }
    }

    public void removerAssociacoesDoProjeto(Long projetoId) {
        try {
            List<Membro> membros = membroGateway.findByProjetoId(projetoId);
            for (Membro membro : membros) {
                membroGateway.delete(membro);
                log.info("Membro removido do projeto ID: {} com sucesso", projetoId);
            }
        } catch (Exception e) {
            log.error("Erro ao buscar membros para o projeto ID: {}", projetoId, e);
            throw new MembroException("Erro ao remover associações do projeto", e);
        }
    }
    public void removerFuncionarioDoProjeto(Long projetoId, Long funcionarioId) {
        try {
            Optional<Membro> membroOptional = membroGateway.findByProjetoIdAndPessoaId(projetoId, funcionarioId);
            if (membroOptional.isPresent()) {
                Membro membro = membroOptional.get();
                membroGateway.delete(membro);
                log.info("Membro com ID: {} removido do projeto ID: {} com sucesso", funcionarioId, projetoId);
            } else {
                log.warn("Membro não encontrado para o projeto ID: {} e funcionário ID: {}", projetoId, funcionarioId);
            }
        } catch (Exception e) {
            log.error("Erro ao remover funcionário ID: {} do projeto ID: {}", funcionarioId, projetoId, e);
            throw new MembroException("Erro ao remover funcionário do projeto", e);
        }
    }


}
