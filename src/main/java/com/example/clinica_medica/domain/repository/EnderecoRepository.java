package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.model.Endereco;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByMedicoId(Long id);

    List<Endereco> findByPacienteId(Long id);

    void deleteByIdAndMedicoId(Long idEndereco, Long idMedico);

    void deleteByIdAndPacienteId(Long idEndereco, Long idMedico);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE educamed.endereco
        SET bairro=:bairro,
            cep=:cep,
            cidade=:cidade,
            complemento=:complemento,
            numero=:numero,
            rua=:rua
        WHERE medico_id=:idMedico
            AND id=:idEndereco;
    """, nativeQuery = true)
    void updateByMedicoId(
            @Param("idMedico")Long idMedico,
            @Param("idEndereco")Long idEndereco,
            @Param("bairro") String bairro,
            @Param("cep") String cep,
            @Param("cidade") String cidade,
            @Param("complemento") String complemento,
            @Param("numero") String numero,
            @Param("rua") String rua
    );

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE educamed.endereco
        SET bairro=:bairro,
            cep=:cep,
            cidade=:cidade,
            complemento=:complemento,
            numero=:numero,
            rua=:rua
        WHERE paciente_id=:idPaciente
            AND id=:idEndereco;
    """, nativeQuery = true)
    void updateByPacienteId(
            @Param("idPaciente")Long idPaciente,
            @Param("idEndereco")Long idEndereco,
            @Param("bairro") String bairro,
            @Param("cep") String cep,
            @Param("cidade") String cidade,
            @Param("complemento") String complemento,
            @Param("numero") String numero,
            @Param("rua") String rua
    );

    Endereco findOneById(Long idEndereco);
}
