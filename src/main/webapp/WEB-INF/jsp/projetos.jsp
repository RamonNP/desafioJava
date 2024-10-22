<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt_br">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gerenciar Projetos</title>
    <link href="<c:url value='/static/node_modules/bootstrap/dist/css/bootstrap.min.css'/>" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        function excluirProjeto(id) {
            $.ajax({
                url: '<c:url value="/projetos/" />' + id,
                type: 'DELETE',
                success: function(response) {
                    // Mensagem de sucesso
                    mostrarMensagem('Projeto excluído com sucesso!', 'success');
                    setTimeout(function() {
                        location.reload(); // Recarrega a página para atualizar a lista de projetos
                    }, 1000); // Aguarda 1 segundo antes de recarregar
                },
                error: function(xhr) {
                    // Mensagem de erro
                    mostrarMensagem('Erro ao excluir o projeto: ' + xhr.responseText, 'danger');
                }
            });
        }

        function mostrarMensagem(mensagem, tipo) {
            const toastHTML = `
                <div class="toast align-items-center text-bg-`+tipo+` border-0" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="d-flex">
                        <div class="toast-body">`+
                            mensagem +
                        `</div>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                </div>`;
            $('#mensagem').append(toastHTML);
            const toastElement = $('.toast').last(); // Pega o último toast adicionado
            const toast = new bootstrap.Toast(toastElement);
            toast.show(); // Mostra o toast
            setTimeout(() => {
                toastElement.remove(); // Remove o toast após 5 segundos
            }, 5000); // Aguarda 5 segundos antes de remover
        }
    </script>
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
                    <a class="nav-link active" aria-current="page" href="<c:url value='/projetos' />">Gerenciar Projetos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/membros' />">Associar Membros</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <h1 class="mt-4">Lista de Projetos</h1>

    <!-- Div para exibir mensagens -->
    <div id="mensagem" style="position: absolute; top: 70px; right: 20px; z-index: 1050;"></div>

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
            <c:forEach var="projeto" items="${projetos}">
                <tr>
                    <td>${projeto.id}</td>
                    <td>${projeto.nome}</td>
                    <td>${projeto.dataInicio}</td>
                    <td>${projeto.dataPrevisaoFim}</td>
                    <td>${projeto.status}</td>
                    <td>
                        <a href="<c:url value='/projetos/${projeto.id}'/>" class="btn btn-primary">Editar</a>
                        <button class="btn btn-danger" onclick="excluirProjeto(${projeto.id});">Excluir</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="<c:url value='/projetos/new'/>" class="btn btn-success">Novo Projeto</a>
</div>

<script src="<c:url value='/static/node_modules/bootstrap/dist/js/bootstrap.bundle.min.js'/>"></script>
</body>
</html>
