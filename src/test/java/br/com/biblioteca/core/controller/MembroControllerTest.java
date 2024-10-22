package br.com.biblioteca.core.controller;

import br.com.biblioteca.core.model.*;
import br.com.biblioteca.core.service.MembroService;
import br.com.biblioteca.core.service.ProjetoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MembroControllerTest {

    @Mock
    private MembroService membroService;

    @Mock
    private ProjetoService projetoService;

    @Mock
    private Model model;

    @InjectMocks
    private MembroController membroController;

    private List<Pessoa> funcionarios;
    private List<Projeto> projetos;
    private List<Projeto> projetosAssociados;
    private Pessoa gerente;

    @BeforeEach
    void setUp() {
        // Configuração do gerente
        gerente = new Pessoa(1L, "Gerente Silva", "123.456.789-00", false, true);

        // Configuração dos funcionários
        funcionarios = Arrays.asList(
                new Pessoa(2L, "Funcionário 1", "111.222.333-44", true, false),
                new Pessoa(3L, "Funcionário 2", "555.666.777-88", true, false)
        );

        // Configuração dos projetos
        Projeto projeto1 = new Projeto(
                1L,
                "Projeto 1",
                LocalDate.now(),
                LocalDate.now().plusMonths(6),
                null,
                "Descrição do Projeto 1",
                StatusProjeto.EM_ANDAMENTO,
                100000.0,
                Risco.MEDIO,
                gerente,
                List.of()
        );

        Projeto projeto2 = new Projeto(
                2L,
                "Projeto 2",
                LocalDate.now(),
                LocalDate.now().plusMonths(3),
                null,
                "Descrição do Projeto 2",
                StatusProjeto.INICIADO,
                50000.0,
                Risco.BAIXO,
                gerente,
                funcionarios
        );

        projetos = Arrays.asList(projeto1, projeto2);
        projetosAssociados = List.of(projeto2);
    }

    @Test
    void mostrarFormularioAssociacao_DeveRetornarViewCorreta() {
        // Arrange
        when(membroService.findAllFuncionarios()).thenReturn(funcionarios);
        when(projetoService.findAll()).thenReturn(projetos);
        when(projetoService.findAllComFuncionarios()).thenReturn(projetosAssociados);

        // Act
        String viewName = membroController.mostrarFormularioAssociacao(model);

        // Assert
        assertEquals("associar_membros", viewName);
        verify(model).addAttribute("funcionarios", funcionarios);
        verify(model).addAttribute("projetos", projetos);
        verify(model).addAttribute("projetosAssociados", projetosAssociados);
    }

    @Test
    void associarMembros_DeveAssociarERedirecionarCorretamente() {
        // Arrange
        Long projetoId = 1L;
        List<Long> funcionarioIds = Arrays.asList(2L, 3L);

        // Act
        String viewName = membroController.associarMembros(projetoId, funcionarioIds);

        // Assert
        assertEquals("redirect:/membros", viewName);
        verify(membroService).associarFuncionariosAoProjeto(projetoId, funcionarioIds);
    }

    @Test
    void removerAssociacaoTodos_DeveRemoverERedirecionarCorretamente() {
        // Arrange
        Long projetoId = 1L;

        // Act
        String viewName = membroController.removerAssociacao(projetoId);

        // Assert
        assertEquals("redirect:/membros", viewName);
        verify(membroService).removerAssociacoesDoProjeto(projetoId);
    }

    @Test
    void removerAssociacaoFuncionario_DeveRemoverERedirecionarCorretamente() {
        // Arrange
        Long projetoId = 1L;
        Long funcionarioId = 2L;

        // Act
        String viewName = membroController.removerAssociacao(projetoId, funcionarioId);

        // Assert
        assertEquals("redirect:/membros", viewName);
        verify(membroService).removerFuncionarioDoProjeto(projetoId, funcionarioId);
    }

    @Test
    void mostrarFormularioAssociacao_QuandoNaoHaFuncionarios() {
        // Arrange
        when(membroService.findAllFuncionarios()).thenReturn(List.of());
        when(projetoService.findAll()).thenReturn(projetos);
        when(projetoService.findAllComFuncionarios()).thenReturn(projetosAssociados);

        // Act
        String viewName = membroController.mostrarFormularioAssociacao(model);

        // Assert
        assertEquals("associar_membros", viewName);
        verify(model).addAttribute("funcionarios", List.of());
        verify(model).addAttribute("projetos", projetos);
        verify(model).addAttribute("projetosAssociados", projetosAssociados);
    }

    @Test
    void mostrarFormularioAssociacao_QuandoNaoHaProjetos() {
        // Arrange
        when(membroService.findAllFuncionarios()).thenReturn(funcionarios);
        when(projetoService.findAll()).thenReturn(List.of());
        when(projetoService.findAllComFuncionarios()).thenReturn(List.of());

        // Act
        String viewName = membroController.mostrarFormularioAssociacao(model);

        // Assert
        assertEquals("associar_membros", viewName);
        verify(model).addAttribute("funcionarios", funcionarios);
        verify(model).addAttribute("projetos", List.of());
        verify(model).addAttribute("projetosAssociados", List.of());
    }
}