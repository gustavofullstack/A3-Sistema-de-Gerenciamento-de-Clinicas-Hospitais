package com.example.clinica_medica.domain.dto;

import com.example.clinica_medica.domain.enuns.Genero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PacienteSimplificadoDto {
    private Long id;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private Genero genero;
}
