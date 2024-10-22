
# Desafio Java - Gestão de Portfólio de Projetos

Este projeto consiste em um sistema para gerenciar os dados do portfólio de projetos de uma empresa. Ele permite o cadastro, exclusão, alteração e consulta de projetos, além da associação de funcionários a esses projetos.

## Funcionalidades

- Cadastro de novos projetos com nome, datas, orçamento, descrição e status.
- Classificação dos projetos em baixo, médio ou alto risco.
- Alteração e exclusão de projetos (com restrições baseadas no status).
- Associação de funcionários a projetos, recebendo dados via endpoint JSON.

## Tecnologias Utilizadas

- **Java 16**
- **Spring Boot 2.6.5**
- **JPA/Hibernate** para persistência no banco de dados.
- **PostgreSQL** como banco de dados.
- **Maven** para gerenciamento de dependências.
- **JUnit 5** para testes unitários.
- **JSP** como engine de templates para as páginas web.
- **Bootstrap** para a interface visual.

## Pré-requisitos

- **JDK 16** instalado.
- **PostgreSQL** configurado com o banco de dados `portfolioDB`:
    - **Usuário**: `devuser`
    - **Senha**: `devpassword`
- **Maven** instalado.
- **Git** para controle de versão.

## Instalação e Execução

1. **Clone o repositório**:
    ```bash
    git clone https://github.com/RamonNP/desafioJava
    cd desafio-java
    ```

2. **Configuração do Banco de Dados**:
    - Verifique se o PostgreSQL está rodando e o banco de dados `portfolioDB` está criado.
    - Certifique-se de que as credenciais de acesso (usuário e senha) estão corretas no arquivo `application.yml`.

3. **Rodar o projeto**:
    - Acesse a pasta raiz do projeto e execute o comando Maven para iniciar a aplicação:
    ```bash
    mvn spring-boot:run
    ```

4. **Acessar a aplicação**:
    - Com a aplicação rodando, você pode acessá-la através do navegador no seguinte endereço:
      ```
      http://localhost:8080
      ```

## Estrutura de Pacotes

- **br.com.biblioteca.core.controller**: Contém os controladores para gerenciar requisições HTTP.
- **br.com.biblioteca.core.model**: Contém as classes de modelo usadas no sistema.
- **br.com.biblioteca.core.service**: Contém a lógica de negócios e serviços relacionados aos projetos e membros.
- **br.com.biblioteca.config**: Configurações da aplicação.

## Executar Testes

Este projeto contém testes unitários para validar as regras de negócio. Para executar os testes:

```bash
mvn test
```

## Exemplo de Chamada da API

Para cadastrar uma nova pessoa via API, você pode usar o seguinte endpoint:

- **Endpoint**: `POST http://localhost:8080/api/pessoas`
- **Corpo da Requisição**:
    ```json
    {
        "nome": "Juliana do bonde",
        "cpf": "111.456.55-00",
        "datanascimento": "1980-01-01",
        "funcionario": true,
        "gerente": false
    }
    ```
- **Resposta**:
    ```json
    {
        "id": 1,
        "nome": "Juliana do bonde",
        "cpf": "111.456.55-00",
        "datanascimento": "1980-01-01",
        "funcionario": true,
        "gerente": false
    }
    ```


