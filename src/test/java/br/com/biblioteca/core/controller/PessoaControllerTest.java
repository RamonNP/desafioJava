package br.com.biblioteca.core.controller;

import br.com.biblioteca.core.model.Pessoa;
import br.com.biblioteca.core.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaControllerTest {

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaController pessoaController;

    private Pessoa pessoa1;
    private Pessoa pessoa2;
    private List<Pessoa> pessoaList;

    @BeforeEach
    void setUp() {
        // Configuração dos dados de teste
        pessoa1 = new Pessoa(1L, "João Silva", "123.456.789-00", true, false);
        pessoa2 = new Pessoa(2L, "Maria Santos", "987.654.321-00", false, true);
        pessoaList = Arrays.asList(pessoa1, pessoa2);
    }

    @Test
    void createPessoa_QuandoSucesso_DeveRetornarPessoaCriada() {
        // Arrange
        Pessoa novaPessoa = new Pessoa(null, "Carlos Silva", "111.222.333-44", true, false);
        when(pessoaService.save(any(Pessoa.class))).thenReturn(
                new Pessoa(3L, "Carlos Silva", "111.222.333-44", true, false)
        );

        // Act
        ResponseEntity<Pessoa> response = pessoaController.createPessoa(novaPessoa);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Carlos Silva", response.getBody().getNome());
        verify(pessoaService).save(any(Pessoa.class));
    }

    @Test
    void createPessoa_QuandoErro_DeveRetornarInternalServerError() {
        // Arrange
        Pessoa novaPessoa = new Pessoa(null, "Carlos Silva", "111.222.333-44", true, false);
        when(pessoaService.save(any(Pessoa.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        // Act
        ResponseEntity<Pessoa> response = pessoaController.createPessoa(novaPessoa);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(pessoaService).save(any(Pessoa.class));
    }

    @Test
    void listarPessoas_QuandoSucesso_DeveRetornarListaDePessoas() {
        // Arrange
        when(pessoaService.findAll()).thenReturn(pessoaList);

        // Act
        ResponseEntity<List<Pessoa>> response = pessoaController.listarPessoas();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(pessoaService).findAll();
    }

    @Test
    void listarPessoas_QuandoErro_DeveRetornarInternalServerError() {
        // Arrange
        when(pessoaService.findAll()).thenThrow(new RuntimeException("Erro ao listar"));

        // Act
        ResponseEntity<List<Pessoa>> response = pessoaController.listarPessoas();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(pessoaService).findAll();
    }

    @Test
    void alterarPessoa_QuandoSucesso_DeveRetornarPessoaAlterada() {
        // Arrange
        Long id = 1L;
        Pessoa pessoaParaAlterar = new Pessoa(id, "João Silva Alterado", "123.456.789-00", true, false);
        when(pessoaService.save(any(Pessoa.class))).thenReturn(pessoaParaAlterar);

        // Act
        ResponseEntity<Pessoa> response = pessoaController.alterarPessoa(id, pessoaParaAlterar);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("João Silva Alterado", response.getBody().getNome());
        assertEquals(id, response.getBody().getId());
        verify(pessoaService).save(any(Pessoa.class));
    }

    @Test
    void alterarPessoa_QuandoErro_DeveRetornarInternalServerError() {
        // Arrange
        Long id = 1L;
        Pessoa pessoaParaAlterar = new Pessoa(id, "João Silva Alterado", "123.456.789-00", true, false);
        when(pessoaService.save(any(Pessoa.class))).thenThrow(new RuntimeException("Erro ao alterar"));

        // Act
        ResponseEntity<Pessoa> response = pessoaController.alterarPessoa(id, pessoaParaAlterar);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(pessoaService).save(any(Pessoa.class));
    }

    @Test
    void excluirPessoa_QuandoSucesso_DeveRetornarNoContent() {
        // Arrange
        Long id = 1L;
        doNothing().when(pessoaService).deleteById(id);

        // Act
        ResponseEntity<Void> response = pessoaController.excluirPessoa(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pessoaService).deleteById(id);
    }

    @Test
    void excluirPessoa_QuandoErro_DeveRetornarNotFound() {
        // Arrange
        Long id = 1L;
        doThrow(new RuntimeException("Erro ao excluir")).when(pessoaService).deleteById(id);

        // Act
        ResponseEntity<Void> response = pessoaController.excluirPessoa(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(pessoaService).deleteById(id);
    }

    @Test
    void createPessoa_QuandoPessoaInvalida_DeveRetornarInternalServerError() {
        // Arrange
        Pessoa pessoaInvalida = new Pessoa(null, "", null, false, false);
        when(pessoaService.save(any(Pessoa.class))).thenThrow(new IllegalArgumentException("Dados inválidos"));

        // Act
        ResponseEntity<Pessoa> response = pessoaController.createPessoa(pessoaInvalida);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(pessoaService).save(any(Pessoa.class));
    }
}