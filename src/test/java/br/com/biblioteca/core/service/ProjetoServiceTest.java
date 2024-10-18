package br.com.biblioteca.core.service;

import br.com.biblioteca.core.model.Pessoa;
import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.core.repository.ProjetoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test") // Use um perfil de teste para a configuração do banco de dados
@AutoConfigureMockMvc
@Transactional
class ProjetoServiceTest {

    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private ProjetoRepository projetoRepository;

    @BeforeEach
    void setUp() {
        projetoRepository.deleteAll(); // Limpa a tabela antes de cada teste
    }

    @Test
    void testSalvarProjeto() {

        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Teste");
        projeto.setDescricao("Descrição do projeto teste");
        projeto.setStatus("em análise");
        projeto.setRisco("baixo");

        // Crie um objeto Pessoa para ser usado como gerente
        Pessoa gerente = new Pessoa();
        gerente.setNome("Nome do Gerente");
        // Preencha os outros atributos necessários

        // Salve o gerente antes de associá-lo ao projeto
        pessoaRepository.save(gerente);

        Projeto savedProjeto = projetoService.salvar(projeto);

        Assertions.assertNotNull(savedProjeto.getId()); // Verifica se o ID foi gerado
        Assertions.assertEquals("Projeto Teste", savedProjeto.getNome());
    }

    @Test
    void testListarProjetos() {
        Projeto projeto1 = new Projeto();
        projeto1.setNome("Projeto Teste 1");
        projeto1.setDescricao("Descrição do projeto teste 1");
        projeto1.setStatus("em análise");
        projeto1.setRisco("baixo");

        Projeto projeto2 = new Projeto();
        projeto2.setNome("Projeto Teste 2");
        projeto2.setDescricao("Descrição do projeto teste 2");
        projeto2.setStatus("em análise");
        projeto2.setRisco("médio");

        projetoService.salvar(projeto1);
        projetoService.salvar(projeto2);

        List<Projeto> projetos = projetoService.listarTodos();
        Assertions.assertEquals(2, projetos.size()); // Verifica se os projetos foram salvos corretamente
    }

    @Test
    void testExcluirProjeto() {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Teste");
        projeto.setDescricao("Descrição do projeto teste");
        projeto.setStatus("em análise");
        projeto.setRisco("baixo");

        Projeto savedProjeto = projetoService.salvar(projeto);
        projetoService.excluir(savedProjeto.getId());

        Assertions.assertFalse(projetoRepository.findById(savedProjeto.getId()).isPresent()); // Verifica se o projeto foi excluído
    }

    @Test
    void testAtualizarProjeto() {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Teste");
        projeto.setDescricao("Descrição do projeto teste");
        projeto.setStatus("em análise");
        projeto.setRisco("baixo");

        Projeto savedProjeto = projetoService.salvar(projeto);
        savedProjeto.setNome("Projeto Teste Atualizado");

        Projeto updatedProjeto = projetoService.salvar(savedProjeto);

        Assertions.assertEquals("Projeto Teste Atualizado", updatedProjeto.getNome()); // Verifica se o nome foi atualizado
    }
}
