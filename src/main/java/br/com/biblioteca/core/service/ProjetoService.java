package br.com.biblioteca.core.service;

import br.com.biblioteca.core.gateway.ProjetoGateway;
import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.core.model.StatusProjeto; // Importar o enum
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProjetoService {

    private final ProjetoGateway projetoGateway;

    public List<Projeto> findAll() {
        try {
            List<Projeto> projetos = projetoGateway.findAll();
            log.info("Lista de projetos recuperada com sucesso.");
            return projetos;
        } catch (Exception e) {
            log.error("Erro ao recuperar a lista de projetos: {}", e.getMessage());
            throw e; // Re-lançar a exceção para tratamento posterior
        }
    }

    public Projeto save(Projeto projeto) {
        try {
            Projeto savedProjeto = projetoGateway.save(projeto);
            log.info("Projeto com ID {} salvo com sucesso.", savedProjeto.getId());
            return savedProjeto;
        } catch (Exception e) {
            log.error("Erro ao salvar o projeto: {}", e.getMessage());
            throw e; // Re-lançar a exceção para tratamento posterior
        }
    }

    public Optional<Projeto> findById(Long id) {
        try {
            Optional<Projeto> projetoOpt = projetoGateway.findById(id);
            if (projetoOpt.isPresent()) {
                log.info("Projeto com ID {} encontrado.", id);
            } else {
                log.warn("Projeto com ID {} não encontrado.", id);
            }
            return projetoOpt;
        } catch (Exception e) {
            log.error("Erro ao buscar projeto com ID {}: {}", id, e.getMessage());
            throw e; // Re-lançar a exceção para tratamento posterior
        }
    }

    public void deleteById(Long id) {
        try {
            Optional<Projeto> projetoOpt = projetoGateway.findById(id);
            if (projetoOpt.isPresent()) {
                Projeto projeto = projetoOpt.get();
                // Verifica se o status do projeto impede a exclusão
                if (projeto.getStatus() == StatusProjeto.INICIADO ||
                        projeto.getStatus() == StatusProjeto.EM_ANDAMENTO ||
                        projeto.getStatus() == StatusProjeto.ENCERRADO) {
                    log.warn("Tentativa de exclusão de projeto com ID {} que está no status '{}' não permitida.", id, projeto.getStatus());
                    throw new IllegalStateException("Não é possível excluir um projeto com status 'iniciado', 'em andamento' ou 'encerrado'.");
                }
                projetoGateway.deleteById(id);
                log.info("Projeto com ID {} excluído com sucesso.", id);
            } else {
                log.error("Projeto com ID {} não encontrado para exclusão.", id);
                throw new IllegalArgumentException("Projeto não encontrado.");
            }
        } catch (Exception e) {
            log.error("Erro ao excluir o projeto com ID {}: {}", id, e.getMessage());
            throw e; // Re-lançar a exceção para tratamento posterior
        }
    }

    public Projeto update(Long id, Projeto projeto) {
        try {
            if (projetoGateway.findById(id).isPresent()) {
                projeto.setId(id);
                Projeto updatedProjeto = projetoGateway.save(projeto);
                log.info("Projeto com ID {} atualizado com sucesso.", id);
                return updatedProjeto;
            } else {
                log.warn("Tentativa de atualização de projeto com ID {} que não existe.", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Erro ao atualizar o projeto com ID {}: {}", id, e.getMessage());
            throw e; // Re-lançar a exceção para tratamento posterior
        }
    }

    public List<Projeto> findAllComFuncionarios() {
        try {
            List<Projeto> projetosComFuncionarios = projetoGateway.findAllComFuncionarios();
            log.info("Lista de projetos com funcionários recuperada com sucesso.");
            return projetosComFuncionarios;
        } catch (Exception e) {
            log.error("Erro ao recuperar a lista de projetos com funcionários: {}", e.getMessage());
            throw e; // Re-lançar a exceção para tratamento posterior
        }
    }

}
