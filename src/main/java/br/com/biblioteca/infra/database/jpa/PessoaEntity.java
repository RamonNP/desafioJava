package br.com.biblioteca.infra.database.jpa;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "pessoa")
@Data
public class PessoaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private boolean funcionario;
    private boolean gerente;
}
