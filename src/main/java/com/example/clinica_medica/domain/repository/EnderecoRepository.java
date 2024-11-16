package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByMedicoId(Long id);

    List<Endereco> findByPacienteId(Long id);

}
