package br.com.biblioteca.infra.database.jpa;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pessoa")
@Data
public class PessoaEntity implements Serializable {

    private static final long serialVersionUID = 1L; // Adicione esta linha
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private boolean funcionario;
    private boolean gerente;
}
