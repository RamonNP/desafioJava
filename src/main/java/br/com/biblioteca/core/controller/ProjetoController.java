package br.com.biblioteca.core.controller;

import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.core.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @GetMapping
    public String getAllProjetos(Model model) {
        List<Projeto> projetos = projetoService.findAll();
        model.addAttribute("projetos", projetos);
        return "projetos"; // Nome da JSP que será renderizada
    }

    @GetMapping("/{id}")
    public String getProjetoById(@PathVariable Long id, Model model) {
        var projeto = projetoService.findById(id);
        model.addAttribute("projeto", projeto.orElse(null));
        return "projeto-detalhes"; // Nome da JSP para detalhes do projeto
    }

    @PostMapping
    public String createProjeto(@ModelAttribute Projeto projeto) {
        projetoService.save(projeto);
        return "redirect:/projetos";
    }

    @PutMapping("/{id}")
    public String updateProjeto(@PathVariable Long id, @ModelAttribute Projeto projeto) {
        projetoService.update(id, projeto);
        return "redirect:/projetos";
    }

    @DeleteMapping("/{id}")
    public String deleteProjeto(@PathVariable Long id) {
        projetoService.deleteById(id);
        return "redirect:/projetos";
    }

    @GetMapping("/new")
    public String novoProjetoForm(Model model) {
        model.addAttribute("projeto", new Projeto());
        return "projeto-form"; // Nome da JSP para o formulário de criação de novo projeto
    }
}
