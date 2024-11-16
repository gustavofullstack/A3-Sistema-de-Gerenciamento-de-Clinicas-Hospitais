package com.example.clinica_medica.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContatoDto {

    private Long id;
    private String telefone;
    private String email;

}
