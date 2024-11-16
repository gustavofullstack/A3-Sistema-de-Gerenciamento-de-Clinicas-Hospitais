package com.example.clinica_medica.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDto {

    private Long id;
    private String rua;
    private String cep;
    private String bairro;
    private String numero;
    private String cidade;
    private String complemento;
    private PacienteDto paciente;
    private MedicoDto medico;

}
