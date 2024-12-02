package com.example.clinica_medica.domain.dto;

import com.example.clinica_medica.domain.enuns.Genero;
import com.example.clinica_medica.domain.model.Consulta;
import com.example.clinica_medica.domain.model.HistoricoMedico;
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
public class PacienteDto {

    private Long id;

    @NotNull(message = "Nome não pode ser nulo")
    private String nome;

    @NotNull(message = "CPF não pode ser nulo")
    private String cpf;

    @NotNull(message = "Data de nascimento não pode ser nula")
    private Date dataNascimento;

    @NotNull(message = "Genero não pode ser nulo")
    private Genero genero;

    private List<EnderecoDto> enderecos;
    private List<ContatoDto> contatos;

    private List<HistoricoMedicoSimplificadoDto> historicoMedico;
    private List<ConsultaSimplificadaDto> consultas;

}
