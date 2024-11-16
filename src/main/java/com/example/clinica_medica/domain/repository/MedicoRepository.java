package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.model.Medico;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Medico findOneById(Long id);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE educamed.medico 
        SET data_nascimento = :dataNascimento, 
            cpf = :cpf, 
            nome = :nome, 
            genero = :genero,
            numero_registro = :numeroRegistro, 
            especializacao = :especializacao 
        WHERE id = :id
    """, nativeQuery = true)
    void updateById(
            @Param("id")Long id,
            @Param("dataNascimento") Date dataNascimento,
            @Param("cpf") String cpf,
            @Param("nome") String nome,
            @Param("genero") String genero,
            @Param("numeroRegistro") String numeroRegistro,
            @Param("especializacao") String especializacao
    );

}
