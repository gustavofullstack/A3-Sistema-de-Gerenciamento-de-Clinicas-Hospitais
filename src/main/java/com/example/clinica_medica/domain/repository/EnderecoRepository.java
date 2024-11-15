package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
