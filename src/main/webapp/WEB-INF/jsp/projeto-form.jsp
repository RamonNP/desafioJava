<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt_br">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cadastrar Projeto</title>
    <link href="<c:url value='/static/node_modules/bootstrap/dist/css/bootstrap.min.css'/>" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
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
                    <a class="nav-link" href="<c:url value='/'/>">Início</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/projetos'/>">Gerenciar Projetos</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <h2 class="text-center">${projeto.id == null ? 'Cadastrar Novo Projeto' : 'Editar Projeto'}</h2>
    <form action="<c:url value='/projetos'/>" method="post">
        <input type="hidden" name="id" value="${projeto.id}" />

        <div class="mb-4">
            <h5>Informações Básicas</h5>
            <div class="mb-3">
                <label for="nome" class="form-label">Nome do Projeto</label>
                <input type="text" class="form-control" id="nome" name="nome" value="${projeto.nome}" required placeholder="Digite o nome do projeto" style="max-width: 400px;">
            </div>
            <div class="mb-3">
                <label for="descricao" class="form-label">Descrição</label>
                <textarea class="form-control" id="descricao" name="descricao" rows="3" required placeholder="Descreva brevemente o projeto">${projeto.descricao}</textarea>
            </div>
        </div>

        <div class="mb-4">
            <h5>Datas</h5>
            <div class="row">
                <div class="col-md-4 mb-3">
                    <label for="dataInicio" class="form-label">Data de Início</label>
                    <input type="date" class="form-control" id="dataInicio" name="dataInicio"
                           value="${projeto.dataInicio}" required>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="dataPrevisaoFim" class="form-label">Previsão de Término</label>
                    <input type="date" class="form-control" id="dataPrevisaoFim" name="dataPrevisaoFim"
                           value="${projeto.dataPrevisaoFim}" required>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="dataFim" class="form-label">Data Real de Término</label>
                    <input type="date" class="form-control" id="dataFim" name="dataFim"
                           value="${projeto.dataFim}" placeholder="Caso já tenha terminado">
                </div>
            </div>
        </div>

        <div class="mb-4">
            <h5>Finanças</h5>
            <div class="mb-3">
                <label for="orcamento" class="form-label">Orçamento Total (R$)</label>
                <input type="text" class="form-control" id="orcamento" name="orcamento" value="${projeto.orcamento}" required placeholder="Ex.: 1.000,00" style="max-width: 200px;">
            </div>
        </div>

        <div class="mb-4">
            <h5>Outras Informações</h5>
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="status" class="form-label">Status</label>
                    <select class="form-select" id="status" name="status" required>
                        <option value="EM_ANALISE" ${projeto.status == 'EM_ANALISE' ? 'selected' : ''}>Em Análise</option>
                        <option value="ANALISE_REALIZADA" ${projeto.status == 'ANALISE_REALIZADA' ? 'selected' : ''}>Análise Realizada</option>
                        <option value="ANALISE_APROVADA" ${projeto.status == 'ANALISE_APROVADA' ? 'selected' : ''}>Análise Aprovada</option>
                        <option value="PLANEJADO" ${projeto.status == 'PLANEJADO' ? 'selected' : ''}>Planejado</option>
                        <option value="INICIADO" ${projeto.status == 'INICIADO' ? 'selected' : ''}>Iniciado</option>
                        <option value="EM_ANDAMENTO" ${projeto.status == 'EM_ANDAMENTO' ? 'selected' : ''}>Em Andamento</option>
                        <option value="ENCERRADO" ${projeto.status == 'ENCERRADO' ? 'selected' : ''}>Encerrado</option>
                        <option value="CANCELADO" ${projeto.status == 'CANCELADO' ? 'selected' : ''}>Cancelado</option>
                    </select>
                </div>

                <div class="col-md-6">
                    <label for="risco" class="form-label">Classificação de Risco</label>
                    <select class="form-select" id="risco" name="risco" required>
                        <option value="BAIXO" ${projeto.risco == 'BAIXO' ? 'selected' : ''}>Baixo Risco</option>
                        <option value="MEDIO" ${projeto.risco == 'MEDIO' ? 'selected' : ''}>Médio Risco</option>
                        <option value="ALTO" ${projeto.risco == 'ALTO' ? 'selected' : ''}>Alto Risco</option>
                    </select>
                </div>
            </div>

            <div class="mb-3">
                <label for="gerente" class="form-label">Gerente Responsável</label>
                <select class="form-control" id="gerente" name="gerente.id" required style="max-width: 250px;">
                    <option value="">Selecione um Gerente</option>
                    <c:forEach var="gerente" items="${gerentes}">
                        <option value="${gerente.id}" <c:if test="${projeto.gerente.id == gerente.id}">selected</c:if>>${gerente.nome}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">Salvar</button
