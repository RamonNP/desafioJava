package br.com.biblioteca.core.controller;

import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.core.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {
    @Autowired
    private ProjetoService projetoService;

    @GetMapping
    public List<Projeto> listar() {
        return projetoService.listarTodos();
    }

    @PostMapping
    public Projeto criar(@RequestBody Projeto projeto) {
        return projetoService.salvar(projeto);
    }

    @PutMapping("/{id}")
    public Projeto atualizar(@PathVariable Long id, @RequestBody Projeto projeto) {
        projeto.setId(id);
        return projetoService.salvar(projeto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        projetoService.excluir(id);
    }

    @GetMapping("/{id}")
    public Projeto buscarPorId(@PathVariable Long id) {
        return projetoService.buscarPorId(id);
    }
}
