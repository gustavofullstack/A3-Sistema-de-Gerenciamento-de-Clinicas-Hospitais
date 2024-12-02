package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.model.Prescricao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface PrescricaoRepository extends JpaRepository<Prescricao, Long> {

    Prescricao findOneById(Long id);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE educamed.prescricao
        SET consulta_id=:idConsulta,
            dosagem=:dosagem,
            instrucoes_uso=:instrucoesUso,
            medicamento=:medicamento
        WHERE id=:idPrescricao;
    """, nativeQuery = true)
    void updateById(
            @Param("idConsulta")Long idConsulta,
            @Param("idPrescricao") Long idPrescricao,
            @Param("dosagem") String dosagem,
            @Param("instrucoesUso") String instrucoesUso,
            @Param("medicamento") String medicamento
    );

}
