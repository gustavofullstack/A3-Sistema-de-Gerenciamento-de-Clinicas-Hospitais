package com.example.clinica_medica.domain.dto;

import com.example.clinica_medica.domain.model.Medico;
import com.example.clinica_medica.domain.model.Paciente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContatoDto {

    private Long id;
    private String telefone;
    private String email;
    private PacienteDto paciente;
    private MedicoDto medico;

}
