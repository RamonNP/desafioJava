package br.com.biblioteca.core.controller;

import br.com.biblioteca.core.model.Pessoa;
import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.core.service.PessoaService;
import br.com.biblioteca.core.service.ProjetoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/projetos")
@AllArgsConstructor
public class ProjetoController {

    private final ProjetoService projetoService;
    private final PessoaService pessoaService;

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
        List<Pessoa> gerentes = pessoaService.findGerentes(); // Obter gerentes
        model.addAttribute("gerentes", gerentes); // Adiciona ao modelo
        return "projeto-form"; // Nome da JSP para detalhes do projeto
    }

    @PostMapping
    public String createProjeto(@ModelAttribute Projeto projeto) {
        log.info("Iniciando o processo de criação de um novo projeto: {}", projeto);

        try {
            projetoService.save(projeto);
            log.info("Projeto criado com sucesso: {}", projeto.getNome());
            return "redirect:/projetos";
        } catch (Exception e) {
            log.error("Erro ao criar o projeto: {} - Detalhes: {}", projeto, e.getMessage(), e);
            return "error"; // Supondo que há uma página de erro
        }
    }

    @PutMapping("/{id}")
    public String updateProjeto(@PathVariable Long id, @ModelAttribute Projeto projeto) {
        projetoService.update(id, projeto);
        return "redirect:/projetos";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProjeto(@PathVariable Long id) {
        try {
            projetoService.deleteById(id);
            return ResponseEntity.ok("Projeto excluído com sucesso."); // Retorna uma resposta OK
        } catch (Exception e) {
            log.error("Erro ao excluir o projeto com ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // Retorna erro
        }
    }


    @GetMapping("/new")
    public String novoProjetoForm(Model model) {
        model.addAttribute("projeto", new Projeto());
        List<Pessoa> gerentes = pessoaService.findGerentes(); // Obter gerentes
        model.addAttribute("gerentes", gerentes); // Adiciona ao modelo
        return "projeto-form"; // Nome da JSP para o formulário de criação de novo projeto
    }



}
