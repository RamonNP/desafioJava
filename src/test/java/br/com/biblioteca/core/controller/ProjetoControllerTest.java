package br.com.biblioteca.core.controller;

import br.com.biblioteca.core.model.Pessoa;
import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.core.model.Risco;
import br.com.biblioteca.core.model.StatusProjeto;
import br.com.biblioteca.core.service.PessoaService;
import br.com.biblioteca.core.service.ProjetoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjetoControllerTest {

    @Mock
    private ProjetoService projetoService;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private Model model;

    private ProjetoController projetoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projetoController = new ProjetoController(projetoService, pessoaService);
    }

    @Test
    void getAllProjetos_ShouldReturnProjetosView() {
        // Arrange
        List<Projeto> projetos = Arrays.asList(createSampleProjeto(), createSampleProjeto());
        when(projetoService.findAll()).thenReturn(projetos);

        // Act
        String viewName = projetoController.getAllProjetos(model);

        // Assert
        verify(model).addAttribute("projetos", projetos);
        assertEquals("projetos", viewName);
    }

    @Test
    void getProjetoById_WhenProjetoExists_ShouldReturnProjetoForm() {
        // Arrange
        Long id = 1L;
        Projeto projeto = createSampleProjeto();
        List<Pessoa> gerentes = Arrays.asList(createSampleGerente());

        when(projetoService.findById(id)).thenReturn(Optional.of(projeto));
        when(pessoaService.findGerentes()).thenReturn(gerentes);

        // Act
        String viewName = projetoController.getProjetoById(id, model);

        // Assert
        verify(model).addAttribute("projeto", projeto);
        verify(model).addAttribute("gerentes", gerentes);
        assertEquals("projeto-form", viewName);
    }

    @Test
    void createProjeto_WhenSuccessful_ShouldRedirectToProjetos() {
        // Arrange
        Projeto projeto = createSampleProjeto();
        when(projetoService.save(any(Projeto.class))).thenReturn(projeto);

        // Act
        String viewName = projetoController.createProjeto(projeto);

        // Assert
        verify(projetoService).save(projeto);
        assertEquals("redirect:/projetos", viewName);
    }

    @Test
    void createProjeto_WhenError_ShouldReturnErrorView() {
        // Arrange
        Projeto projeto = createSampleProjeto();
        when(projetoService.save(any(Projeto.class))).thenThrow(new RuntimeException("Error"));

        // Act
        String viewName = projetoController.createProjeto(projeto);

        // Assert
        assertEquals("error", viewName);
    }

    @Test
    void updateProjeto_ShouldRedirectToProjetos() {
        // Arrange
        Long id = 1L;
        Projeto projeto = createSampleProjeto();

        // Act
        String viewName = projetoController.updateProjeto(id, projeto);

        // Assert
        verify(projetoService).update(id, projeto);
        assertEquals("redirect:/projetos", viewName);
    }

    @Test
    void deleteProjeto_WhenSuccessful_ShouldReturnOkResponse() {
        // Arrange
        Long id = 1L;
        doNothing().when(projetoService).deleteById(id);

        // Act
        ResponseEntity<String> response = projetoController.deleteProjeto(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Projeto excluído com sucesso.", response.getBody());
    }

    @Test
    void deleteProjeto_WhenError_ShouldReturnErrorResponse() {
        // Arrange
        Long id = 1L;
        doThrow(new RuntimeException("Error")).when(projetoService).deleteById(id);

        // Act
        ResponseEntity<String> response = projetoController.deleteProjeto(id);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao excluir o projeto.", response.getBody());
    }

    @Test
    void novoProjetoForm_ShouldReturnProjetoFormWithGerentesList() {
        // Arrange
        List<Pessoa> gerentes = Arrays.asList(createSampleGerente());
        when(pessoaService.findGerentes()).thenReturn(gerentes);

        // Act
        String viewName = projetoController.novoProjetoForm(model);

        // Assert
        verify(model).addAttribute(eq("projeto"), any(Projeto.class));
        verify(model).addAttribute("gerentes", gerentes);
        assertEquals("projeto-form", viewName);
    }

    // Helper methods to create sample objects for testing
    private Projeto createSampleProjeto() {
        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto Teste");
        projeto.setDataInicio(LocalDate.now());
        projeto.setDataPrevisaoFim(LocalDate.now().plusMonths(6));
        projeto.setStatus(StatusProjeto.EM_ANDAMENTO);
        projeto.setOrcamento(100000.0);
        projeto.setRisco(Risco.BAIXO);
        projeto.setGerente(createSampleGerente());
        return projeto;
    }

    private Pessoa createSampleGerente() {
        Pessoa gerente = new Pessoa();
        gerente.setId(1L);
        gerente.setNome("Gerente Teste");
        gerente.setCpf("12345678900");
        gerente.setGerente(true);
        return gerente;
    }
}