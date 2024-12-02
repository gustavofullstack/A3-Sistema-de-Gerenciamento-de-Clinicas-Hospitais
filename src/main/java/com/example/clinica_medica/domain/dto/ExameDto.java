package com.example.clinica_medica.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExameDto {

    private Long id;

    private String nomeExame;

    private String resultado;

    private Date dataExame;

    private ConsultaSimplificadaDto consulta;

}
