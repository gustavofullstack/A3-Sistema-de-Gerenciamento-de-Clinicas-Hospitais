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
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telefone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @ToString.Exclude
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    @ToString.Exclude
    private Medico medico;
}
