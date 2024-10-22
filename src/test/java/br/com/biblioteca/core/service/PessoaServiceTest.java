package br.com.biblioteca.core.service;

import br.com.biblioteca.core.gateway.PessoaGateway;
import br.com.biblioteca.core.model.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaServiceTest {

    @Mock
    private PessoaGateway pessoaGateway;

    @InjectMocks
    private PessoaService pessoaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_Success() {
        // Setup
        List<Pessoa> pessoas = Arrays.asList(new Pessoa(), new Pessoa());
        when(pessoaGateway.findAll()).thenReturn(pessoas);

        // Execute
        List<Pessoa> result = pessoaService.findAll();

        // Verify
        assertEquals(2, result.size());
        verify(pessoaGateway, times(1)).findAll();
    }

    @Test
    void findAll_Exception() {
        // Setup
        when(pessoaGateway.findAll()).thenThrow(new RuntimeException("Erro ao buscar todas as pessoas"));

        // Execute & Verify
        Exception exception = assertThrows(RuntimeException.class, () -> pessoaService.findAll());
        assertEquals("Erro ao buscar todas as pessoas", exception.getMessage());
        verify(pessoaGateway, times(1)).findAll();
    }

    @Test
    void save_Success() {
        // Setup
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João");
        when(pessoaGateway.save(pessoa)).thenReturn(pessoa);

        // Execute
        Pessoa result = pessoaService.save(pessoa);

        // Verify
        assertNotNull(result);
        assertEquals("João", result.getNome());
        verify(pessoaGateway, times(1)).save(pessoa);
    }

    @Test
    void save_Exception() {
        // Setup
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Maria");
        when(pessoaGateway.save(pessoa)).thenThrow(new RuntimeException("Erro ao salvar pessoa"));

        // Execute & Verify
        Exception exception = assertThrows(RuntimeException.class, () -> pessoaService.save(pessoa));
        assertEquals("Erro ao salvar pessoa", exception.getMessage());
        verify(pessoaGateway, times(1)).save(pessoa);
    }

    @Test
    void findById_Success() {
        // Setup
        Long pessoaId = 1L;
        Pessoa pessoa = new Pessoa();
        pessoa.setId(pessoaId);
        when(pessoaGateway.findById(pessoaId)).thenReturn(Optional.of(pessoa));

        // Execute
        Pessoa result = pessoaService.findById(pessoaId);

        // Verify
        assertNotNull(result);
        assertEquals(pessoaId, result.getId());
        verify(pessoaGateway, times(1)).findById(pessoaId);
    }

    @Test
    void findById_NotFound() {
        // Setup
        Long pessoaId = 1L;
        when(pessoaGateway.findById(pessoaId)).thenReturn(Optional.empty());

        // Execute
        Pessoa result = pessoaService.findById(pessoaId);

        // Verify
        assertNull(result);
        verify(pessoaGateway, times(1)).findById(pessoaId);
    }

    @Test
    void deleteById_Success() {
        // Setup
        Long pessoaId = 1L;

        // Execute
        pessoaService.deleteById(pessoaId);

        // Verify
        verify(pessoaGateway, times(1)).deleteById(pessoaId);
    }

    @Test
    void deleteById_Exception() {
        // Setup
        Long pessoaId = 1L;
        doThrow(new RuntimeException("Erro ao excluir pessoa")).when(pessoaGateway).deleteById(pessoaId);

        // Execute & Verify
        Exception exception = assertThrows(RuntimeException.class, () -> pessoaService.deleteById(pessoaId));
        assertEquals("Erro ao excluir pessoa", exception.getMessage());
        verify(pessoaGateway, times(1)).deleteById(pessoaId);
    }

    @Test
    void findGerentes_Success() {
        // Setup
        List<Pessoa> gerentes = Arrays.asList(new Pessoa(), new Pessoa());
        when(pessoaGateway.findGerentes()).thenReturn(gerentes);

        // Execute
        List<Pessoa> result = pessoaService.findGerentes();

        // Verify
        assertEquals(2, result.size());
        verify(pessoaGateway, times(1)).findGerentes();
    }

    @Test
    void findGerentes_Exception() {
        // Setup
        when(pessoaGateway.findGerentes()).thenThrow(new RuntimeException("Erro ao buscar gerentes"));

        // Execute & Verify
        Exception exception = assertThrows(RuntimeException.class, () -> pessoaService.findGerentes());
        assertEquals("Erro ao buscar gerentes", exception.getMessage());
        verify(pessoaGateway, times(1)).findGerentes();
    }
}
