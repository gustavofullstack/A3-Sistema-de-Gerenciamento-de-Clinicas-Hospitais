package com.example.clinica_medica.domain.dto;

import com.example.clinica_medica.domain.enuns.Especializacao;
import com.example.clinica_medica.domain.model.Consulta;
import com.example.clinica_medica.domain.model.Contato;
import com.example.clinica_medica.domain.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoDto {

    private Long id;
    private String nome;
    private String numeroRegistro;
    private Especializacao especializacao;
    private Endereco endereco;
    private Contato contato;
    private List<Consulta> consultas;

}
