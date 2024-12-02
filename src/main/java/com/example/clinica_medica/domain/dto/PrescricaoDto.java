package com.example.clinica_medica.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PrescricaoDto {

    private Long id;

    private String medicamento;

    private String dosagem;

    private String instrucoesUso;

    private ConsultaSimplificadaDto consulta;

}
