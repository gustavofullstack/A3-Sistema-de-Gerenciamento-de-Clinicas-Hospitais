package com.example.clinica_medica.domain.dto;

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
    private String telefone;
    private String email;

}
