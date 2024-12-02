package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.model.Exame;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ExameRespository extends JpaRepository<Exame, Long> {

    Exame findOneById(Long id);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE educamed.exame
        SET consulta_id=:idConsulta,
            data_exame=:dataExame,
            nome_exame=:nomeExame,
            resultado=:resultado
        WHERE id=:idExame;
    """, nativeQuery = true)
    void updateById(
            @Param("idExame") Long idExame,
            @Param("idConsulta")Long idConsulta,
            @Param("dataExame") Date dataExame,
            @Param("nomeExame") String nomeExame,
            @Param("resultado") String resultado
    );
}
