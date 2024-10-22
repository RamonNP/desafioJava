package br.com.biblioteca.infra.dataprovider;

import br.com.biblioteca.core.model.Membro;
import br.com.biblioteca.core.model.Pessoa;
import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.infra.database.jpa.MembroEntity;
import br.com.biblioteca.infra.database.jpa.PessoaEntity;
import br.com.biblioteca.infra.database.jpa.ProjetoEntity;
import br.com.biblioteca.infra.repository.MembroRepository;
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

class MembroProviderTest {

    @Mock
    private MembroRepository membroRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MembroProvider membroProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_Success() {
        // Setup
        Membro membro = new Membro();
        MembroEntity membroEntity = new MembroEntity();
        when(modelMapper.map(membro, MembroEntity.class)).thenReturn(membroEntity);
        when(membroRepository.save(membroEntity)).thenReturn(membroEntity);
        when(modelMapper.map(membroEntity, Membro.class)).thenReturn(membro);

        // Execute
        Membro savedMembro = membroProvider.save(membro);

        // Verify
        assertNotNull(savedMembro);
        verify(membroRepository, times(1)).save(membroEntity);
        verify(modelMapper, times(1)).map(membro, MembroEntity.class);
        verify(modelMapper, times(1)).map(membroEntity, Membro.class);
    }

    @Test
    void save_Exception() {
        // Setup
        Membro membro = new Membro();
        Pessoa pessoa = new Pessoa();
        Projeto projeto = new Projeto();
        membro.setPessoa(pessoa);
        membro.setProjeto(projeto);
        when(modelMapper.map(membro, MembroEntity.class)).thenThrow(new RuntimeException("Erro ao salvar membro"));

        // Execute & Verify
        Exception exception = assertThrows(RuntimeException.class, () -> membroProvider.save(membro));
        assertEquals("Erro ao salvar membro", exception.getMessage());
        verify(modelMapper, times(1)).map(membro, MembroEntity.class);
        verify(membroRepository, times(0)).save(any());
    }

    @Test
    void findAll_Success() {
        // Setup
        List<MembroEntity> membroEntities = Arrays.asList(new MembroEntity(), new MembroEntity());
        List<Membro> membros = Arrays.asList(new Membro(), new Membro());
        when(membroRepository.findAll()).thenReturn(membroEntities);
        when(modelMapper.map(membroEntities.get(0), Membro.class)).thenReturn(membros.get(0));
        when(modelMapper.map(membroEntities.get(1), Membro.class)).thenReturn(membros.get(1));

        // Execute
        List<Membro> result = membroProvider.findAll();

        // Verify
        assertEquals(2, result.size());
        verify(membroRepository, times(1)).findAll();
    }

    @Test
    void findAll_EmptyList() {
        // Setup
        when(membroRepository.findAll()).thenReturn(List.of());

        // Execute
        List<Membro> result = membroProvider.findAll();

        // Verify
        assertTrue(result.isEmpty());
        verify(membroRepository, times(1)).findAll();
    }

    @Test
    void findById_Success() {
        // Setup
        Long id = 1L;
        MembroEntity membroEntity = new MembroEntity();
        membroEntity.setPessoa(new PessoaEntity());
        membroEntity.setProjeto(new ProjetoEntity());
        Membro membro = new Membro();
        when(membroRepository.findById(id)).thenReturn(Optional.of(membroEntity));
        when(modelMapper.map(membroEntity, Membro.class)).thenReturn(membro);

        // Execute
        Optional<Membro> result = membroProvider.findById(id);

        // Verify
        assertTrue(result.isPresent());
        verify(membroRepository, times(1)).findById(id);
    }

    @Test
    void findById_NotFound() {
        // Setup
        Long id = 1L;
        when(membroRepository.findById(id)).thenReturn(Optional.empty());

        // Execute
        Optional<Membro> result = membroProvider.findById(id);

        // Verify
        assertTrue(result.isEmpty());
        verify(membroRepository, times(1)).findById(id);
    }

    @Test
    void deleteById_Success() {
        // Setup
        Long id = 1L;

        // Execute
        membroProvider.deleteById(id);

        // Verify
        verify(membroRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteById_Exception() {
        // Setup
        Long id = 1L;
        doThrow(new RuntimeException("Erro ao excluir membro")).when(membroRepository).deleteById(id);

        // Execute & Verify
        Exception exception = assertThrows(RuntimeException.class, () -> membroProvider.deleteById(id));
        assertEquals("Erro ao excluir membro", exception.getMessage());
        verify(membroRepository, times(1)).deleteById(id);
    }

    @Test
    void findByProjetoId_Success() {
        // Setup
        Long projetoId = 1L;
        List<MembroEntity> membroEntities = Arrays.asList(new MembroEntity(), new MembroEntity());
        List<Membro> membros = Arrays.asList(new Membro(), new Membro());
        when(membroRepository.findByProjetoId(projetoId)).thenReturn(membroEntities);
        when(modelMapper.map(membroEntities.get(0), Membro.class)).thenReturn(membros.get(0));
        when(modelMapper.map(membroEntities.get(1), Membro.class)).thenReturn(membros.get(1));

        // Execute
        List<Membro> result = membroProvider.findByProjetoId(projetoId);

        // Verify
        assertEquals(2, result.size());
        verify(membroRepository, times(1)).findByProjetoId(projetoId);
    }

    @Test
    void findByProjetoIdAndPessoaId_Success() {
        // Setup
        Long projetoId = 1L;
        Long pessoaId = 2L;
        MembroEntity membroEntity = new MembroEntity();
        Membro membro = new Membro();
        when(membroRepository.findByProjetoIdAndPessoaId(projetoId, pessoaId)).thenReturn(Optional.of(membroEntity));
        when(modelMapper.map(membroEntity, Membro.class)).thenReturn(membro);

        // Execute
        Optional<Membro> result = membroProvider.findByProjetoIdAndPessoaId(projetoId, pessoaId);

        // Verify
        assertTrue(result.isPresent());
        verify(membroRepository, times(1)).findByProjetoIdAndPessoaId(projetoId, pessoaId);
    }

    @Test
    void findByProjetoIdAndPessoaId_NotFound() {
        // Setup
        Long projetoId = 1L;
        Long pessoaId = 2L;
        when(membroRepository.findByProjetoIdAndPessoaId(projetoId, pessoaId)).thenReturn(Optional.empty());

        // Execute
        Optional<Membro> result = membroProvider.findByProjetoIdAndPessoaId(projetoId, pessoaId);

        // Verify
        assertTrue(result.isEmpty());
        verify(membroRepository, times(1)).findByProjetoIdAndPessoaId(projetoId, pessoaId);
    }
}
