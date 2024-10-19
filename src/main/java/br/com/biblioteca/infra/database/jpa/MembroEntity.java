package br.com.biblioteca.infra.database.jpa;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "membro")
@Data
public class MembroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idprojeto", nullable = false)
    private ProjetoEntity projeto; // Relacionamento com a classe Projeto

    @ManyToOne
    @JoinColumn(name = "idpessoa", nullable = false)
    private PessoaEntity pessoa; // Relacionamento com a classe Pessoa
}
