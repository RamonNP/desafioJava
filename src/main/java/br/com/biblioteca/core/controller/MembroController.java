package br.com.biblioteca.core.controller;

import br.com.biblioteca.core.model.Membro;
import br.com.biblioteca.core.model.Pessoa;
import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.core.service.MembroService;
import br.com.biblioteca.core.service.PessoaService;
import br.com.biblioteca.core.service.ProjetoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class MembroController {

    private final MembroService membroService;
    private final ProjetoService projetoService;


    @GetMapping("/membros")
    public String mostrarFormularioAssociacao(Model model) {
        List<Pessoa> funcionarios = membroService.findAllFuncionarios();
        List<Projeto> projetos = projetoService.findAll();
        List<Projeto> projetosAssociados = projetoService.findAllComFuncionarios(); // Obtenha a lista de projetos com funcionários associados
        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("projetos", projetos);
        model.addAttribute("projetosAssociados", projetosAssociados);
        return "associar_membros"; // Nome da JSP que será renderizada
    }


    @PostMapping("/membros")
    public String associarMembros(@RequestParam("projetoId") Long projetoId,
                                  @RequestParam("funcionarioIds") List<Long> funcionarioIds) {
        membroService.associarFuncionariosAoProjeto(projetoId, funcionarioIds); // Associa os funcionários ao projeto
        return "redirect:/membros"; // Redireciona para a página de associação
    }

    @PostMapping("/membros/removerTodos")
    public String removerAssociacao(@RequestParam("projetoId") Long projetoId) {
        membroService.removerAssociacoesDoProjeto(projetoId); // Remove todas as associações de um projeto
        return "redirect:/membros"; // Redireciona para a página de associação após a remoção
    }

    @PostMapping("/membros/remover")
    public String removerAssociacao(@RequestParam("projetoId") Long projetoId,
                                    @RequestParam("funcionarioId") Long funcionarioId) {
        membroService.removerFuncionarioDoProjeto(projetoId, funcionarioId); // Remove a associação de um funcionário específico do projeto
        return "redirect:/membros"; // Redireciona para a página de associação após a remoção
    }


}
