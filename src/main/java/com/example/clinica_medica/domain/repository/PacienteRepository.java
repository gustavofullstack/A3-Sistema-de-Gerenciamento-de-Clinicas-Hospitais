package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.model.Paciente;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Paciente findOneById(Long id);


}
