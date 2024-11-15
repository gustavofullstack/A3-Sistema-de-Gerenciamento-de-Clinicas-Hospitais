package com.example.clinica_medica.domain.dto;

import com.example.clinica_medica.domain.enuns.Genero;
import com.example.clinica_medica.domain.model.Consulta;
import com.example.clinica_medica.domain.model.Contato;
import com.example.clinica_medica.domain.model.Endereco;
import com.example.clinica_medica.domain.model.HistoricoMedico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDto {

    private Long id;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private Genero genero;
    private List<EnderecoDto> enderecos;
    private List<ContatoDto> contatos;
    private List<HistoricoMedico> historicoMedico;
    private List<Consulta> consultas;

}
