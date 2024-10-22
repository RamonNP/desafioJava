package br.com.biblioteca.core.service;

import br.com.biblioteca.core.gateway.MembroGateway;
import br.com.biblioteca.core.gateway.PessoaGateway;
import br.com.biblioteca.core.model.Membro;
import br.com.biblioteca.core.model.Pessoa;
import br.com.biblioteca.core.model.Projeto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class MembroServiceTest  {

    @Mock
    private MembroGateway membroGateway;

    @Mock
    private PessoaGateway pessoaGateway;

    @Mock
    private ProjetoService projetoService;

    @InjectMocks
    private MembroService membroService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllFuncionarios() {
        // Simulando retorno do gateway
        Pessoa funcionario = new Pessoa(1L, "Funcionário 1", "12345678900", true, false);
        when(pessoaGateway.findAllFuncionarios()).thenReturn(List.of(funcionario));

        List<Pessoa> funcionarios = membroService.findAllFuncionarios();

        assertNotNull(funcionarios);
        assertEquals(1, funcionarios.size());
        assertEquals("Funcionário 1", funcionarios.get(0).getNome());
        verify(pessoaGateway, times(1)).findAllFuncionarios();
    }

    @Test
    void testAssociarFuncionariosAoProjeto_Success() {
        Long projetoId = 1L;
        Long funcionarioId = 1L;

        Projeto projeto = new Projeto();
        projeto.setId(projetoId);

        Pessoa funcionario = new Pessoa(funcionarioId, "Funcionário 1", "12345678900", true, false);
        Membro membro = new Membro();
        membro.setPessoa(funcionario);
        membro.setProjeto(projeto);

        // Mocking dependencies
        when(projetoService.findById(projetoId)).thenReturn(Optional.of(projeto));
        when(pessoaGateway.findById(funcionarioId)).thenReturn(Optional.of(funcionario));
        when(membroGateway.save(any(Membro.class))).thenReturn(membro);

        membroService.associarFuncionariosAoProjeto(projetoId, List.of(funcionarioId));

        // Verificando interações
        verify(projetoService, times(1)).findById(projetoId);
        verify(pessoaGateway, times(1)).findById(funcionarioId);
        verify(membroGateway, times(1)).save(any(Membro.class));
    }

    @Test
    void testRemoverFuncionarioDoProjeto_Success() {
        Long projetoId = 1L;
        Long funcionarioId = 1L;

        Membro membro = new Membro();
        when(membroGateway.findByProjetoIdAndPessoaId(projetoId, funcionarioId)).thenReturn(Optional.of(membro));

        membroService.removerFuncionarioDoProjeto(projetoId, funcionarioId);

        verify(membroGateway, times(1)).delete(membro);
    }

    @Test
    void testRemoverFuncionarioDoProjeto_NotFound() {
        Long projetoId = 1L;
        Long funcionarioId = 1L;

        // Mock para simular ausência de membro
        when(membroGateway.findByProjetoIdAndPessoaId(projetoId, funcionarioId)).thenReturn(Optional.empty());

        membroService.removerFuncionarioDoProjeto(projetoId, funcionarioId);

        verify(membroGateway, never()).delete(any(Membro.class));
    }
}
