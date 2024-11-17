package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.model.Consulta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Consulta findOneById(Long idConsulta);

    @Modifying
    @Transactional
    @Query(value = """
    UPDATE educamed.consulta
    SET data_hora=:dataHora,
        medico_id=:idMedico,
        paciente_id=:idPaciente,
        motivo_consulta=:motivoConsulta
    WHERE id=:idConsulta
    """, nativeQuery = true)
    void updateById(
            @Param("idConsulta") Long idConsulta,
            @Param("idPaciente") Long idPaciente,
            @Param("idMedico") Long idMedico,
            @Param("dataHora") Date dataHora,
            @Param("motivoConsulta") String motivoConsulta
    );
}
