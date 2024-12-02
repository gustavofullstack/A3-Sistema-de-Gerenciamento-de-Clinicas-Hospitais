package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.model.HistoricoMedico;
import com.example.clinica_medica.domain.model.Paciente;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface HistoricoMedicoRespository extends JpaRepository<HistoricoMedico, Long> {

    HistoricoMedico findOneById(Long id);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE educamed.historico_medico
        SET paciente_id=:idPaciente,
            alergias=:alergias,
            condicao=:condicao,
            medicamentos_em_uso=:medicamentosEmUso,
            tratamento=:tratamento
        WHERE id=:idHistorico;
    """, nativeQuery = true)
    void updateById(@Param("idPaciente") Long idPaciente,
                    @Param("idHistorico") Long idHistorico,
                    @Param("alergias") String alergias,
                    @Param("condicao") String condicao,
                    @Param("medicamentosEmUso") String medicamentosEmUso,
                    @Param("tratamento") String tratamento
    );

    @Modifying
    @Transactional
    @Query(value = """
    INSERT INTO educamed.historico_medico
    (alergias, condicao, medicamentos_em_uso, tratamento, paciente_id)
    VALUES(:alergias, :condicao, :medicamentosEmUso, :tratamento, :idPaciente)
    """, nativeQuery = true)
    void adicionarHistorico(@Param("idPaciente") Long idPaciente,
                            @Param("alergias") String alergias,
                            @Param("condicao") String condicao,
                            @Param("medicamentosEmUso") String medicamentosEmUso,
                            @Param("tratamento") String tratamento);
}
