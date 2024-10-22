package br.com.biblioteca;


import br.com.biblioteca.core.gateway.ProjetoGateway;
import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.core.model.Risco;
import br.com.biblioteca.core.model.StatusProjeto;
import br.com.biblioteca.core.service.MembroService;
import br.com.biblioteca.core.service.ProjetoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
class ResgrasNegocioTest {

    @Mock
    private ProjetoGateway projetoGateway;

    @Mock
    private ProjetoService projetoServiceMock;
    @InjectMocks
    private ProjetoService projetoService;
    @InjectMocks
    private MembroService membroService;

    private Projeto projeto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projeto = new Projeto();
        projeto.setNome("Projeto Teste");
        projeto.setStatus(StatusProjeto.EM_ANALISE);
    }

    // Regra 1: Cadastro de projetos
    @Test
    void devePermitirCadastroDeProjeto() {
        when(projetoGateway.save(projeto)).thenReturn(projeto);
        Projeto salvo = projetoService.save(projeto);
        assertNotNull(salvo);
        assertEquals(projeto.getNome(), salvo.getNome());
    }

    // Regra 2: Exclusão de projetos permitida apenas se não estiver em status 'Iniciado', 'Em andamento' ou 'Encerrado'
    @Test
    void naoDevePermitirExcluirProjetoEmAndamento() {
        projeto.setStatus(StatusProjeto.EM_ANDAMENTO);
        when(projetoGateway.findById(projeto.getId())).thenReturn(Optional.of(projeto));

        // Verifica se IllegalStateException é lançada ao tentar excluir o projeto
        IllegalStateException exception = null;
        try {
            deleteProjectById(projeto.getId());
        } catch (IllegalStateException e) {
            exception = e;
        }

        assertNotNull(exception); // Garante que a exceção foi lançada
        String expectedMessage = "Não é possível excluir um projeto com status 'iniciado', 'em andamento' ou 'encerrado'.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    private void deleteProjectById(Long id) {
        projetoService.deleteById(id);
    }

    // Regra 3: Classificação de risco de projetos
    @Test
    void deveClassificarProjetoCorretamente() {
        projeto.setRisco(Risco.ALTO);
        assertEquals(Risco.ALTO, projeto.getRisco());
    }

    // Regra 4: Não permitir cadastro de membros diretamente
    @Test
    void naoDevePermitirCadastroDeMembroDiretamente() {
        // Teste para garantir que não existe método público de cadastro direto de membros no serviço
        assertThrows(NoSuchMethodException.class, () -> {
            ProjetoService.class.getDeclaredMethod("cadastrarMembroDiretamente");
        });
    }

    // Regra 5: Associar membros com atribuição de funcionário a projetos
    @Test
    void devePermitirAssociarFuncionariosAProjeto() {
        // Simulação de associação de membros
        Long funcionarioId = 1L;
        Long projetoId = 1L;
        List<Long> funcionarioIds = new ArrayList<>();
        funcionarioIds.add(funcionarioId);

        // Cria um mock de Projeto
        Projeto projetoMock = new Projeto(); // Crie e inicialize o objeto Projeto conforme necessário

        // Mock do comportamento do serviço de projeto
        when(projetoServiceMock.findById(projetoId)).thenReturn(Optional.of(projetoMock));

        // Executa o método e verifica se não lança exceção
        assertDoesNotThrow(() -> membroService.associarFuncionariosAoProjeto(projetoId, funcionarioIds));


    }
}
