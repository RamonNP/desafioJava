package br.com.biblioteca.infra.dataprovider;

import br.com.biblioteca.core.model.Membro;
import br.com.biblioteca.core.model.Pessoa;
import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.infra.database.jpa.ProjetoEntity;
import br.com.biblioteca.infra.repository.ProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjetoProviderTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MembroProvider membroGateway;

    @InjectMocks
    private ProjetoProvider projetoProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_Success() {
        // Setup
        Projeto projeto = new Projeto();
        ProjetoEntity projetoEntity = new ProjetoEntity();
        when(modelMapper.map(projeto, ProjetoEntity.class)).thenReturn(projetoEntity);
        when(projetoRepository.save(projetoEntity)).thenReturn(projetoEntity);
        when(modelMapper.map(projetoEntity, Projeto.class)).thenReturn(projeto);

        // Execute
        Projeto savedProjeto = projetoProvider.save(projeto);

        // Verify
        assertNotNull(savedProjeto);
        verify(projetoRepository, times(1)).save(projetoEntity);
        verify(modelMapper, times(1)).map(projeto, ProjetoEntity.class);
        verify(modelMapper, times(1)).map(projetoEntity, Projeto.class);
    }

    @Test
    void save_Exception() {
        // Setup
        Projeto projeto = new Projeto();
        when(modelMapper.map(projeto, ProjetoEntity.class)).thenThrow(new RuntimeException("Erro ao salvar projeto"));

        // Execute & Verify
        Exception exception = assertThrows(RuntimeException.class, () -> projetoProvider.save(projeto));
        assertEquals("Erro ao salvar projeto", exception.getMessage());
        verify(modelMapper, times(1)).map(projeto, ProjetoEntity.class);
        verify(projetoRepository, times(0)).save(any());
    }

    @Test
    void findAll_Success() {
        // Setup
        List<ProjetoEntity> projetoEntities = Arrays.asList(new ProjetoEntity(), new ProjetoEntity());
        List<Projeto> projetos = Arrays.asList(new Projeto(), new Projeto());
        when(projetoRepository.findAll()).thenReturn(projetoEntities);
        when(modelMapper.map(projetoEntities.get(0), Projeto.class)).thenReturn(projetos.get(0));
        when(modelMapper.map(projetoEntities.get(1), Projeto.class)).thenReturn(projetos.get(1));

        // Execute
        List<Projeto> result = projetoProvider.findAll();

        // Verify
        assertEquals(2, result.size());
        verify(projetoRepository, times(1)).findAll();
    }

    @Test
    void findAll_EmptyList() {
        // Setup
        when(projetoRepository.findAll()).thenReturn(List.of());

        // Execute
        List<Projeto> result = projetoProvider.findAll();

        // Verify
        assertTrue(result.isEmpty());
        verify(projetoRepository, times(1)).findAll();
    }

    @Test
    void findById_Success() {
        // Setup
        Long id = 1L;
        ProjetoEntity projetoEntity = new ProjetoEntity();
        Projeto projeto = new Projeto();
        when(projetoRepository.findById(id)).thenReturn(Optional.of(projetoEntity));
        when(modelMapper.map(projetoEntity, Projeto.class)).thenReturn(projeto);

        // Execute
        Optional<Projeto> result = projetoProvider.findById(id);

        // Verify
        assertTrue(result.isPresent());
        verify(projetoRepository, times(1)).findById(id);
    }

    @Test
    void findById_NotFound() {
        // Setup
        Long id = 1L;
        when(projetoRepository.findById(id)).thenReturn(Optional.empty());

        // Execute
        Optional<Projeto> result = projetoProvider.findById(id);

        // Verify
        assertTrue(result.isEmpty());
        verify(projetoRepository, times(1)).findById(id);
    }

    @Test
    void deleteById_Success() {
        // Setup
        Long id = 1L;

        // Execute
        projetoProvider.deleteById(id);

        // Verify
        verify(projetoRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteById_Exception() {
        // Setup
        Long id = 1L;
        doThrow(new RuntimeException("Erro ao deletar projeto")).when(projetoRepository).deleteById(id);

        // Execute & Verify
        Exception exception = assertThrows(RuntimeException.class, () -> projetoProvider.deleteById(id));
        assertEquals("Erro ao deletar projeto", exception.getMessage());
        verify(projetoRepository, times(1)).deleteById(id);
    }

    @Test
    void findAllComFuncionarios_Success() {
        // Setup
        List<ProjetoEntity> projetoEntities = Arrays.asList(new ProjetoEntity(), new ProjetoEntity());
        List<Projeto> projetos = Arrays.asList(new Projeto(), new Projeto());
        List<Membro> membros = Arrays.asList(new Membro(new Pessoa(), new Projeto()), new Membro(new Pessoa(), new Projeto()));

        when(projetoRepository.findAll()).thenReturn(projetoEntities);
        when(modelMapper.map(projetoEntities.get(0), Projeto.class)).thenReturn(projetos.get(0));
        when(modelMapper.map(projetoEntities.get(1), Projeto.class)).thenReturn(projetos.get(1));
        when(membroGateway.findByProjetoId(anyLong())).thenReturn(membros);

        // Execute
        List<Projeto> result = projetoProvider.findAllComFuncionarios();

        // Verify
        assertEquals(2, result.size());
    }

    @Test
    void findAllComFuncionarios_EmptyList() {
        // Setup
        when(projetoRepository.findAll()).thenReturn(List.of());

        // Execute
        List<Projeto> result = projetoProvider.findAllComFuncionarios();

        // Verify
        assertTrue(result.isEmpty());
        verify(projetoRepository, times(1)).findAll();
        verify(membroGateway, times(0)).findByProjetoId(anyLong());
    }
}
