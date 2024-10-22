<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt_br">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sistema de Gestão de Portfólio de Projetos</title>
    <link href="<c:url value="/static/node_modules/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
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
                    <a class="nav-link active" aria-current="page" href="<c:url value="/" />">Início</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/projetos" />">Gerenciar Projetos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/membros" />">Associar Membros</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <h1 class="text-center">Bem-vindo ao Sistema de Gestão de Projetos</h1>
    <p class="text-center">Gerencie seu portfólio de projetos com facilidade e controle total. Aqui você pode cadastrar, consultar, alterar e excluir projetos, além de associar membros da equipe a cada um deles.</p>

    <div class="row mt-4">
        <div class="col-md-4">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h5 class="card-title">Gerenciar Projetos</h5>
                </div>
                <div class="card-body">
                    <p>Crie novos projetos, edite informações e acompanhe o progresso de cada um.</p>
                    <a href="<c:url value='/projetos' />" class="btn btn-primary">Acessar</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-header bg-success text-white">
                    <h5 class="card-title">Associar Membros</h5>
                </div>
                <div class="card-body">
                    <p>Adicione membros aos projetos de acordo com suas atribuições e acompanhe a evolução da equipe.</p>
                    <a href="<c:url value='/membros' />" class="btn btn-success">Acessar</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-header bg-warning text-white">
                    <h5 class="card-title">Relatórios e Consultas</h5>
                </div>
                <div class="card-body">
                    <p>Gere relatórios detalhados sobre o andamento dos projetos e suas análises de viabilidade.</p>
                    <a href="<c:url value='' />" class="btn btn-warning">EM BREVE</a>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value='/static/node_modules/bootstrap/dist/js/bootstrap.bundle.min.js'/>"></script>
</body>
</html>
