package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Medico findOneById(Long id);


}
