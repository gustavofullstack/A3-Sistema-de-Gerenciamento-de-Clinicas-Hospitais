package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
}
