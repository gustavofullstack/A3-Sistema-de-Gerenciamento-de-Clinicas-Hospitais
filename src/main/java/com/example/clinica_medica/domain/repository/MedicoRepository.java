package com.example.clinica_medica.domain.repository;

import com.example.clinica_medica.domain.enuns.Especializacao;
import com.example.clinica_medica.domain.model.Medico;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Medico findOneById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Medico m SET m.nome = :nome, m.numeroRegistro = :numeroRegistro, m.especializacao = :especializacao, " +
            "m.endereco.rua = :rua, m.endereco.cep = :cep, m.endereco.bairro = :bairro, m.endereco.numero = :numero, " +
            "m.endereco.cidade = :cidade, m.endereco.complemento = :complemento, m.contato.telefone = :telefone, " +
            "m.contato.email = :email WHERE m.id = :id")
    void updateById(
            @Param("id") Long id,
            @Param("nome") String nome,
            @Param("numeroRegistro") String numeroRegistro,
            @Param("especializacao") Especializacao especializacao,
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
