package com.example.clinica_medica.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Prescricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicamento;

    private String dosagem;

    private String instrucoesUso;

    @ManyToOne
    @JoinColumn(name = "consulta_id", nullable = false)
    @ToString.Exclude
    private Consulta consulta;
}
