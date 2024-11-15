package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Paciente findOneById(Long id);


}
