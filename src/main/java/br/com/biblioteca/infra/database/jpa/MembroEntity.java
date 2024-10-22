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
    @MapsId("projetoId")
    @JoinColumn(name = "idprojeto", nullable = false)
    private ProjetoEntity projeto;

    @ManyToOne
    @MapsId("pessoaId")
    @JoinColumn(name = "idpessoa", nullable = false)
    private PessoaEntity pessoa;
}
