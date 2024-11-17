package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.model.Contato;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

    List<Contato> findByMedicoId(Long id);

    List<Contato> findByPacienteId(Long id);

    void deleteByIdAndMedicoId(Long idContato, Long idMedico);

    void deleteByIdAndPacienteId(Long idContato, Long idPaciente);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE educamed.contato
        set email=:email,
        telefone=:telefone
        WHERE medico_id=:idMedico;
    """, nativeQuery = true)
    void updateByIdMedico(
            @Param("idMedico")Long idMedico,
            @Param("email") String email,
            @Param("telefone") String telefone
    );

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE educamed.contato
        set email=:email,
        telefone=:telefone
        WHERE paciente_id=:idPaciente;
    """, nativeQuery = true)
    void updateByIdPaciente(
            @Param("idPaciente")Long idPaciente,
            @Param("email") String email,
            @Param("telefone") String telefone
    );

    Contato findOneById(Long idEndereco);

}
