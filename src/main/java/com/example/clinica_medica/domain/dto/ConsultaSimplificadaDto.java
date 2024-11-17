package com.example.clinica_medica.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsultaSimplificadaDto {

    private Long id;

    @NotNull(message = "Data não pode ser nula")
    private Date dataHora;

    @NotNull(message = "Motivo da consulta não pode ser nula")
    private String motivoConsulta;

    @NotNull(message = "Motivo da consulta não pode ser nulo")
    private PacienteSimplificadoDto paciente;

    @NotNull(message = "Medico não pode ser nulo")
    private MedicoSimplificadoDto medico;

}
