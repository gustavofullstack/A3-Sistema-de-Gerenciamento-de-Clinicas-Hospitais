package com.example.clinica_medica.domain.model;

import com.example.clinica_medica.domain.enuns.Especializacao;
import com.example.clinica_medica.domain.enuns.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome não pode ser nulo")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "O CPF não pode ser nulo")
    @Size(min = 11, max = 11, message = "O CPF deve ter exatamente 11 caracteres")
    private String cpf;

    @NotNull(message = "O número de registro não pode ser nulo")
    private String numeroRegistro;

    @NotNull(message = "A data de nascimento não pode ser nula")
    @PastOrPresent(message = "A data de nascimento não pode ser no futuro")
    private Date dataNascimento;

    @Enumerated(EnumType.STRING)
    private Especializacao especializacao;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contato> contatos;

    @OneToMany(mappedBy = "medico")
    private List<Consulta> consultas;

}