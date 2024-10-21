package br.com.biblioteca.infra.database.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MembroId implements Serializable {

    private Long projetoId;
    private Long pessoaId;

    // Implementação de equals e hashCode para garantir a igualdade correta com base nos dois campos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MembroId membroId = (MembroId) o;
        return Objects.equals(projetoId, membroId.projetoId) &&
                Objects.equals(pessoaId, membroId.pessoaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projetoId, pessoaId);
    }
}
