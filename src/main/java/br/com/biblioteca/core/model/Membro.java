package br.com.biblioteca.core.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "membro")
@Data
public class Membro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idprojeto", nullable = false)
    private Projeto projeto; // Relacionamento com a classe Projeto

    @ManyToOne
    @JoinColumn(name = "idpessoa", nullable = false)
    private Pessoa pessoa; // Relacionamento com a classe Pessoa
}
