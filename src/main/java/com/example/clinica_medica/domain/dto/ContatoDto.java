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
public class ContatoDto {

    private Long id;

    @NotNull(message = "Telefone não pode ser nulo")
    private String telefone;

    @NotNull(message = "Email não pode ser nulo")
    private String email;

}
