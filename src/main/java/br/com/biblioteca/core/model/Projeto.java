package br.com.biblioteca.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projeto {
    private Long id;
    private String nome;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate  dataInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPrevisaoFim;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate  dataFim;
    private String descricao;
    private StatusProjeto status;
    private Double orcamento;
    private Risco risco;
    private Pessoa gerente; // Referência ao objeto Gerente (Pessoa)
    @JsonIgnore
    private List<Pessoa> funcionarios; // Nova propriedade para armazenar os membros associados
}
