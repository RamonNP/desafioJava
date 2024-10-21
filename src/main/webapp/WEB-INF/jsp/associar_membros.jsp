<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt_br">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Associar Funcionários a Projetos - Sistema de Gestão de Projetos</title>
    <link href="<c:url value='/static/node_modules/bootstrap/dist/css/bootstrap.min.css'/>" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Gestão de Projetos</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/' />">Início</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/projetos' />">Gerenciar Projetos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="<c:url value='/membros' />">Associar Membros</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <h1 class="text-center">Associar Funcionários a Projetos</h1>

    <!-- Formulário para associar funcionários a um projeto -->
    <form action="<c:url value='/membros' />" method="post" class="mb-5">
        <div class="row">
            <div class="col-md-6">
                <div class="mb-3">
                    <label for="projeto" class="form-label">Selecione um Projeto:</label>
                    <select class="form-select" id="projeto" name="projetoId" required>
                        <c:forEach var="projeto" items="${projetos}">
                            <option value="${projeto.id}">${projeto.nome}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-md-6">
                <div class="mb-3">
                    <label for="funcionarios" class="form-label">Selecione Funcionários:</label>
                    <select class="form-select" id="funcionarios" name="funcionarioIds" multiple size="5" required>
                        <c:forEach var="funcionario" items="${funcionarios}">
                            <option value="${funcionario.id}">${funcionario.nome}</option>
                        </c:forEach>
                    </select>
                    <small class="form-text text-muted">Segure a tecla Ctrl (ou Cmd no Mac) para selecionar múltiplos funcionários.</small>
                </div>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Associar Funcionários ao Projeto</button>
    </form>

    <!-- Listagem de projetos associados com opção de remover individualmente -->
    <h2 class="text-center">Projetos Associados</h2>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Projeto</th>
                <th>Funcionários Associados</th>
                <th>Ação</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="projeto" items="${projetosAssociados}">
                <tr>
                    <td>${projeto.nome}</td>
                    <td>
                        <ul>
                            <c:forEach var="funcionario" items="${projeto.funcionarios}">
                                <li>
                                    ${funcionario.nome}
                                    <!-- Formulário para remover um funcionário específico do projeto -->
                                    <form action="<c:url value='/membros/remover' />" method="post" style="display: inline;">
                                        <input type="hidden" name="projetoId" value="${projeto.id}" />
                                        <input type="hidden" name="funcionarioId" value="${funcionario.id}" />
                                        <button type="submit" class="btn btn-danger btn-sm">Remover</button>
                                    </form>
                                </li>
                            </c:forEach>
                        </ul>
                    </td>
                    <td>
                        <!-- Mantendo a opção de remover todos os funcionários do projeto, caso ainda deseje -->
                        <form action="<c:url value='/membros/removerTodos' />" method="post" style="display: inline;">
                            <input type="hidden" name="projetoId" value="${projeto.id}" />
                            <button type="submit" class="btn btn-warning btn-sm">Remover Todos</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<script src="<c:url value='/static/node_modules/bootstrap/dist/js/bootstrap.bundle.min.js'/>"></script>
</body>
</html>
