package br.com.biblioteca.infra.database.jpa;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "membro")
@Data
@NoArgsConstructor
public class MembroEntity implements Serializable {

    @EmbeddedId
    private MembroId id;

    @ManyToOne
    @MapsId("projetoId")  // Referenciando a chave primária do projeto
    @JoinColumn(name = "idprojeto", nullable = false)
    private ProjetoEntity projeto; // Relacionamento com a classe Projeto

    @ManyToOne
    @MapsId("pessoaId")  // Referenciando a chave primária da pessoa
    @JoinColumn(name = "idpessoa", nullable = false)
    private PessoaEntity pessoa; // Relacionamento com a classe Pessoa
}
