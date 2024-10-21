package br.com.biblioteca.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Membro {
    private Pessoa pessoa;
    @JsonIgnore
    private Projeto projeto;
}
