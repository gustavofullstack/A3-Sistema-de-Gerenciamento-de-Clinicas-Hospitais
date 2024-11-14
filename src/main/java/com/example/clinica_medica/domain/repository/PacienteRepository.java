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

    @Modifying
    @Transactional
    @Query(value = "UPDATE paciente SET nome = :nome, cpf = :cpf, data_nascimento = :dataNascimento, genero = :genero, " +
            "rua = :rua, cep = :cep, bairro = :bairro, numero = :numero, cidade = :cidade, complemento = :complemento, " +
            "telefone = :telefone, email = :email " +
            "WHERE id = :id", nativeQuery = true)
    void updateById(
            @Param("id") Long id,
            @Param("nome") String nome,
            @Param("cpf") String cpf,
            @Param("dataNascimento") Date dataNascimento,
            @Param("genero") String genero,
            @Param("rua") String rua,
            @Param("cep") String cep,
            @Param("bairro") String bairro,
            @Param("numero") String numero,
            @Param("cidade") String cidade,
            @Param("complemento") String complemento,
            @Param("telefone") String telefone,
            @Param("email") String email
    );
}
