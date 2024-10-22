package br.com.biblioteca.core.service;

import br.com.biblioteca.core.gateway.ProjetoGateway;
import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.core.model.StatusProjeto;
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

class ProjetoServiceTest {

    @Mock
    private ProjetoGateway projetoGateway;

    @InjectMocks
    private ProjetoService projetoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_Success() {
        // Setup
        List<Projeto> projetos = Arrays.asList(new Projeto(), new Projeto());
        when(projetoGateway.findAll()).thenReturn(projetos);

        // Execute
        List<Projeto> result = projetoService.findAll();

        // Verify
        assertEquals(2, result.size());
        verify(projetoGateway, times(1)).findAll();
    }

    @Test
    void save_Success() {
        // Setup
        Projeto projeto = new Projeto();
        projeto.setId(1L);
        when(projetoGateway.save(projeto)).thenReturn(projeto);

        // Execute
        Projeto result = projetoService.save(projeto);

        // Verify
        assertEquals(1L, result.getId());
        verify(projetoGateway, times(1)).save(projeto);
    }

    @Test
    void findById_Success() {
        // Setup
        Long projetoId = 1L;
        Projeto projeto = new Projeto();
        when(projetoGateway.findById(projetoId)).thenReturn(Optional.of(projeto));

        // Execute
        Optional<Projeto> result = projetoService.findById(projetoId);

        // Verify
        assertTrue(result.isPresent());
        verify(projetoGateway, times(1)).findById(projetoId);
    }

    @Test
    void findById_NotFound() {
        // Setup
        Long projetoId = 1L;
        when(projetoGateway.findById(projetoId)).thenReturn(Optional.empty());

        // Execute
        Optional<Projeto> result = projetoService.findById(projetoId);

        // Verify
        assertFalse(result.isPresent());
        verify(projetoGateway, times(1)).findById(projetoId);
    }

    @Test
    void deleteById_Success() {
        // Setup
        Long projetoId = 1L;
        Projeto projeto = new Projeto();
        projeto.setStatus(StatusProjeto.PLANEJADO);
        when(projetoGateway.findById(projetoId)).thenReturn(Optional.of(projeto));

        // Execute
        projetoService.deleteById(projetoId);

        // Verify
        verify(projetoGateway, times(1)).deleteById(projetoId);
    }

    @Test
    void deleteById_IllegalState() {
        // Setup
        Long projetoId = 1L;
        Projeto projeto = new Projeto();
        projeto.setStatus(StatusProjeto.INICIADO);
        when(projetoGateway.findById(projetoId)).thenReturn(Optional.of(projeto));

        // Execute & Verify
        assertThrows(IllegalStateException.class, () -> projetoService.deleteById(projetoId));
        verify(projetoGateway, never()).deleteById(projetoId);
    }

    @Test
    void update_Success() {
        // Setup
        Long projetoId = 1L;
        Projeto projeto = new Projeto();
        projeto.setId(projetoId);
        when(projetoGateway.findById(projetoId)).thenReturn(Optional.of(projeto));
        when(projetoGateway.save(projeto)).thenReturn(projeto);

        // Execute
        Projeto updatedProjeto = projetoService.update(projetoId, projeto);

        // Verify
        assertNotNull(updatedProjeto);
        assertEquals(projetoId, updatedProjeto.getId());
        verify(projetoGateway, times(1)).save(projeto);
    }

    @Test
    void update_NotFound() {
        // Setup
        Long projetoId = 1L;
        Projeto projeto = new Projeto();
        when(projetoGateway.findById(projetoId)).thenReturn(Optional.empty());

        // Execute
        Projeto result = projetoService.update(projetoId, projeto);

        // Verify
        assertNull(result);
        verify(projetoGateway, never()).save(projeto);
    }

    @Test
    void findAllComFuncionarios_Success() {
        // Setup
        List<Projeto> projetosComFuncionarios = Arrays.asList(new Projeto(), new Projeto());
        when(projetoGateway.findAllComFuncionarios()).thenReturn(projetosComFuncionarios);

        // Execute
        List<Projeto> result = projetoService.findAllComFuncionarios();

        // Verify
        assertEquals(2, result.size());
        verify(projetoGateway, times(1)).findAllComFuncionarios();
    }

}
