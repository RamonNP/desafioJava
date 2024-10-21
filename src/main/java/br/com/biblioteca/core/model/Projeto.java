package br.com.biblioteca.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projeto {
    private Long id;
    private String nome;
    private Date dataInicio;
    private Date dataPrevisaoFim;
    private Date dataFim;
    private String descricao;
    private StatusProjeto status;
    private Double orcamento;
    private Risco risco;
    private Pessoa gerente; // Referência ao objeto Gerente (Pessoa)
}
