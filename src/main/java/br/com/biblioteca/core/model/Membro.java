package br.com.biblioteca.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Membro {
    private Long id;
    private Pessoa pessoa;  // Referência ao objeto Pessoa
    private Projeto projeto; // Referência ao objeto Projeto
}
