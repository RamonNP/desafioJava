package br.com.biblioteca.core.service;

import br.com.biblioteca.core.gateway.ProjetoGateway;
import br.com.biblioteca.core.model.Projeto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjetoService {

    private final ProjetoGateway projetoGateway;

    public List<Projeto> findAll() {
        return projetoGateway.findAll();
    }

    public Projeto save(Projeto projeto) {
        return projetoGateway.save(projeto);
    }

    public Optional<Projeto> findById(Long id) {
        return projetoGateway.findById(id);
    }

    public void deleteById(Long id) {
        projetoGateway.deleteById(id);
    }

    public Projeto update(Long id, Projeto projeto) {
        if (projetoGateway.findById(id).isPresent()) {
            projeto.setId(id);
            return projetoGateway.save(projeto);
        }
        return null;
    }
}
