package com.example.clinica_medica.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Prescricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicamento;

    private String dosagem;

    private String instrucoesUso;

    @ManyToOne
    @JoinColumn(name = "consulta_id", nullable = false) // Chave estrangeira para a consulta
    private Consulta consulta;
}
