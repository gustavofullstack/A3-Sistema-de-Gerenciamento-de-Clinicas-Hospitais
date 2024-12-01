package com.example.clinica_medica.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EnderecoDto {

    private Long id;

    @NotNull(message = "Rua não pode ser nula")
    private String rua;

    @NotNull(message = "CEP não pode ser nulo")
    private String cep;

    @NotNull(message = "Bairro não pode ser nulo")
    private String bairro;

    @NotNull(message = "Numero não pode ser nulo")
    private String numero;

    @NotNull(message = "Cidade não pode ser nula")
    private String cidade;

    @NotNull(message = "Estado não pode ser nulo")
    private String estado;

    private String complemento;

}
