package br.com.biblioteca.infra.database.jpa;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projeto")
@Data
public class ProjetoEntity implements Serializable {

    private static final long serialVersionUID = 1L; // Adicione esta linha
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataPrevisaoFim;
    private LocalDate dataFim;
    private String descricao;
    private String status;
    private Double orcamento;
    private String risco;

    @ManyToOne
    @JoinColumn(name = "idgerente", nullable = false)
    private PessoaEntity gerente;

    @OneToMany(mappedBy = "projeto")
    private List<MembroEntity> funcionarios;
}
