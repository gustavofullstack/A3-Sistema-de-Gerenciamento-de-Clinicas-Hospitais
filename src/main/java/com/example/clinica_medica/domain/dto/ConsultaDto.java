package com.example.clinica_medica.domain.dto;

import com.example.clinica_medica.domain.model.Exame;
import com.example.clinica_medica.domain.model.Medico;
import com.example.clinica_medica.domain.model.Paciente;
import com.example.clinica_medica.domain.model.Prescricao;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsultaDto {

    private Long id;

    @NotNull(message = "Data não pode ser nula")
    private Date dataHora;

    @NotNull(message = "Motivo da consulta não pode ser nula")
    private String motivoConsulta;

    private String observacoesMedicas;

    @NotNull(message = "Motivo da consulta não pode ser nulo")
    private PacienteSimplificadoDto paciente;

    @NotNull(message = "Medico não pode ser nulo")
    private MedicoSimplificadoDto medico;

    private List<PrescricaoDto> prescricoes;
    private List<ExameDto> exames;

}
