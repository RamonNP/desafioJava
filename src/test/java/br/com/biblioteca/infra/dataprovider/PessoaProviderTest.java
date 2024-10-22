package br.com.biblioteca.infra.dataprovider;

import br.com.biblioteca.core.model.Pessoa;
import br.com.biblioteca.infra.database.jpa.PessoaEntity;
import br.com.biblioteca.infra.repository.PessoaRepository;
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

class PessoaProviderTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PessoaProvider pessoaProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_Success() {
        // Setup
        Pessoa pessoa = new Pessoa();
        PessoaEntity pessoaEntity = new PessoaEntity();
        when(modelMapper.map(pessoa, PessoaEntity.class)).thenReturn(pessoaEntity);
        when(pessoaRepository.save(pessoaEntity)).thenReturn(pessoaEntity);
        when(modelMapper.map(pessoaEntity, Pessoa.class)).thenReturn(pessoa);

        // Execute
        Pessoa savedPessoa = pessoaProvider.save(pessoa);

        // Verify
        assertNotNull(savedPessoa);
        verify(pessoaRepository, times(1)).save(pessoaEntity);
        verify(modelMapper, times(1)).map(pessoa, PessoaEntity.class);
        verify(modelMapper, times(1)).map(pessoaEntity, Pessoa.class);
    }

    @Test
    void save_Exception() {
        // Setup
        Pessoa pessoa = new Pessoa();
        when(modelMapper.map(pessoa, PessoaEntity.class)).thenThrow(new RuntimeException("Erro ao salvar pessoa"));

        // Execute & Verify
        Exception exception = assertThrows(RuntimeException.class, () -> pessoaProvider.save(pessoa));
        assertEquals("Erro ao salvar pessoa", exception.getMessage());
        verify(modelMapper, times(1)).map(pessoa, PessoaEntity.class);
        verify(pessoaRepository, times(0)).save(any());
    }

    @Test
    void findAll_Success() {
        // Setup
        List<PessoaEntity> pessoaEntities = Arrays.asList(new PessoaEntity(), new PessoaEntity());
        List<Pessoa> pessoas = Arrays.asList(new Pessoa(), new Pessoa());
        when(pessoaRepository.findAll()).thenReturn(pessoaEntities);
        when(modelMapper.map(pessoaEntities.get(0), Pessoa.class)).thenReturn(pessoas.get(0));
        when(modelMapper.map(pessoaEntities.get(1), Pessoa.class)).thenReturn(pessoas.get(1));

        // Execute
        List<Pessoa> result = pessoaProvider.findAll();

        // Verify
        assertEquals(2, result.size());
        verify(pessoaRepository, times(1)).findAll();
    }

    @Test
    void findAll_EmptyList() {
        // Setup
        when(pessoaRepository.findAll()).thenReturn(List.of());

        // Execute
        List<Pessoa> result = pessoaProvider.findAll();

        // Verify
        assertTrue(result.isEmpty());
        verify(pessoaRepository, times(1)).findAll();
    }

    @Test
    void findById_Success() {
        // Setup
        Long id = 1L;
        PessoaEntity pessoaEntity = new PessoaEntity();
        Pessoa pessoa = new Pessoa();
        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoaEntity));
        when(modelMapper.map(pessoaEntity, Pessoa.class)).thenReturn(pessoa);

        // Execute
        Optional<Pessoa> result = pessoaProvider.findById(id);

        // Verify
        assertTrue(result.isPresent());
        verify(pessoaRepository, times(1)).findById(id);
    }

    @Test
    void findById_NotFound() {
        // Setup
        Long id = 1L;
        when(pessoaRepository.findById(id)).thenReturn(Optional.empty());

        // Execute
        Optional<Pessoa> result = pessoaProvider.findById(id);

        // Verify
        assertTrue(result.isEmpty());
        verify(pessoaRepository, times(1)).findById(id);
    }

    @Test
    void deleteById_Success() {
        // Setup
        Long id = 1L;

        // Execute
        pessoaProvider.deleteById(id);

        // Verify
        verify(pessoaRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteById_Exception() {
        // Setup
        Long id = 1L;
        doThrow(new RuntimeException("Erro ao excluir pessoa")).when(pessoaRepository).deleteById(id);

        // Execute & Verify
        Exception exception = assertThrows(RuntimeException.class, () -> pessoaProvider.deleteById(id));
        assertEquals("Erro ao excluir pessoa", exception.getMessage());
        verify(pessoaRepository, times(1)).deleteById(id);
    }

    @Test
    void findGerentes_Success() {
        // Setup
        List<PessoaEntity> pessoaEntities = Arrays.asList(new PessoaEntity(), new PessoaEntity());
        List<Pessoa> pessoas = Arrays.asList(new Pessoa(), new Pessoa());
        when(pessoaRepository.findAllByGerenteTrue()).thenReturn(pessoaEntities);
        when(modelMapper.map(pessoaEntities.get(0), Pessoa.class)).thenReturn(pessoas.get(0));
        when(modelMapper.map(pessoaEntities.get(1), Pessoa.class)).thenReturn(pessoas.get(1));

        // Execute
        List<Pessoa> result = pessoaProvider.findGerentes();

        // Verify
        assertEquals(2, result.size());
        verify(pessoaRepository, times(1)).findAllByGerenteTrue();
    }

    @Test
    void findAllFuncionarios_Success() {
        // Setup
        List<PessoaEntity> pessoaEntities = Arrays.asList(new PessoaEntity(), new PessoaEntity());
        List<Pessoa> pessoas = Arrays.asList(new Pessoa(), new Pessoa());
        when(pessoaRepository.findAllByFuncionarioTrue()).thenReturn(pessoaEntities);
        when(modelMapper.map(pessoaEntities.get(0), Pessoa.class)).thenReturn(pessoas.get(0));
        when(modelMapper.map(pessoaEntities.get(1), Pessoa.class)).thenReturn(pessoas.get(1));

        // Execute
        List<Pessoa> result = pessoaProvider.findAllFuncionarios();

        // Verify
        assertEquals(2, result.size());
        verify(pessoaRepository, times(1)).findAllByFuncionarioTrue();
    }

    @Test
    void findGerentes_EmptyList() {
        // Setup
        when(pessoaRepository.findAllByGerenteTrue()).thenReturn(List.of());

        // Execute
        List<Pessoa> result = pessoaProvider.findGerentes();

        // Verify
        assertTrue(result.isEmpty());
        verify(pessoaRepository, times(1)).findAllByGerenteTrue();
    }

    @Test
    void findAllFuncionarios_EmptyList() {
        // Setup
        when(pessoaRepository.findAllByFuncionarioTrue()).thenReturn(List.of());

        // Execute
        List<Pessoa> result = pessoaProvider.findAllFuncionarios();

        // Verify
        assertTrue(result.isEmpty());
        verify(pessoaRepository, times(1)).findAllByFuncionarioTrue();
    }
}
