package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

    List<Contato> findByMedicoId(Long id);

    List<Contato> findByPacienteId(Long id);
}
