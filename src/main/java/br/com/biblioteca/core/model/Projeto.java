package br.com.biblioteca.core.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "projeto")
@Data // Gera automaticamente getters, setters e outros métodos comuns
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Date dataInicio;
    private Date dataPrevisaoFim;
    private Date dataFim;
    private String descricao;
    private String status;
    private Double orcamento;
    private String risco;

    @ManyToOne
    @JoinColumn(name = "idgerente", nullable = false)
    private Pessoa gerente; // Relacionamento com a classe Pessoa
}
