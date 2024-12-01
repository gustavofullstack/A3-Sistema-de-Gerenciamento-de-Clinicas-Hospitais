package com.example.clinica_medica.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rua;
    private String cep;
    private String bairro;
    private String numero;
    private String cidade;
    private String estado;
    private String complemento;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @ToString.Exclude
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    @ToString.Exclude
    private Medico medico;
}
