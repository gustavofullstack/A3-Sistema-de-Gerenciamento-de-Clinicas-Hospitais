package com.example.clinica_medica.domain.model;

import com.example.clinica_medica.domain.enuns.Especializacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String numeroRegistro;

    @Enumerated(EnumType.STRING)
    private Especializacao especializacao;

    @Embedded
    private Endereco endereco;

    @Embedded
    private Contato contato;

    @ManyToOne
    @JoinColumn(name = "disponibilidade_id")
    private Disponibilidade disponibilidade;

    @OneToMany(mappedBy = "medico")
    private List<Consulta> consultas;

}