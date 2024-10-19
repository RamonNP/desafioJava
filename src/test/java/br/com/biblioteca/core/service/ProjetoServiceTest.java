package br.com.biblioteca.core.service;

import br.com.biblioteca.infra.repository.ProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

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


}
