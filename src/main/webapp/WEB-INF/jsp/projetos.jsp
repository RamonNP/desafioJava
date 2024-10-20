<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt_br">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gerenciar Projetos</title>
    <link href="<c:url value="/static/node_modules/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
</head>
<body>

<div class="container">
    <h1 class="mt-4">Lista de Projetos</h1>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Data de Início</th>
                <th>Previsão de Término</th>
                <th>Status</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <!-- Exemplo de repetição para exibir projetos -->
            <c:forEach var="projeto" items="${projetos}">
                <tr>
                    <td>${projeto.id}</td>
                    <td>${projeto.nome}</td>
                    <td>${projeto.dataInicio}</td>
                    <td>${projeto.dataPrevisaoFim}</td>
                    <td>${projeto.status}</td>
                    <td>
                        <a href="<c:url value='/projetos/${projeto.id}'/>" class="btn btn-primary">Editar</a>
                        <a href="<c:url value='/projetos/delete/${projeto.id}'/>" class="btn btn-danger">Excluir</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="<c:url value='/projetos/new'/>" class="btn btn-success">Novo Projeto</a>
</div>

<script src="<c:url value="/static/node_modules/bootstrap/dist/js/bootstrap.min.js"/>"></script>
</body>
</html>
