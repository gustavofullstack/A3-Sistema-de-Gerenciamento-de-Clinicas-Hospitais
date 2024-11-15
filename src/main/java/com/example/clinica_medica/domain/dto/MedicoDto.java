package com.example.clinica_medica.domain.dto;

import com.example.clinica_medica.domain.enuns.Especializacao;
import com.example.clinica_medica.domain.enuns.Genero;
import com.example.clinica_medica.domain.model.Consulta;
import com.example.clinica_medica.domain.model.Contato;
import com.example.clinica_medica.domain.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoDto {

    private Long id;
    private String nome;
    private String cpf;
    private String numeroRegistro;
    private Date dataNascimento;
    private Genero genero;
    private Especializacao especializacao;
    private List<Endereco> enderecos;
    private List<Contato> contatos;
    private List<Consulta> consultas;

}
