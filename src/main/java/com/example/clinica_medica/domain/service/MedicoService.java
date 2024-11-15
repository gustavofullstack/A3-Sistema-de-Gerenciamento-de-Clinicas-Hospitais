package com.example.clinica_medica.domain.service;

import com.example.clinica_medica.domain.dto.MedicoDto;
import com.example.clinica_medica.domain.exception.BusinessException;
import com.example.clinica_medica.domain.model.Medico;
import com.example.clinica_medica.domain.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {
    
    @Autowired
    private MedicoRepository medicoRepository;

    public MedicoDto consultarDadosMedicoPeloId(Long id){

        Medico medico = medicoRepository.findOneById(id);
        MedicoDto medicoDto = toDto(medico);
        return medicoDto;
    }

    public List<MedicoDto> buscarTodosMedicos() throws BusinessException{
        try {

            List<Medico> medicos = medicoRepository.findAll();
            return toDtoList(medicos);

        } catch (Exception e) {
            throw new BusinessException("Não foi possível listar todos os restaurantes.");
        }
    }

    @Transactional
    public void salvarMedico(MedicoDto medicoDto) throws BusinessException{
        Medico medico = toEntity(medicoDto);
        this.medicoRepository.save(medico);
    }

    @Transactional
    public void alterarMedico(MedicoDto medicoDto) throws BusinessException {
        medicoRepository.findById(medicoDto.getId())
                .orElseThrow(() -> new BusinessException(String.format("Médico com ID %d não encontrado para atualização.", medicoDto.getId())));

    }

    @Transactional
    public void deletarMedico(Long idRestaurante) throws BusinessException{
        Medico medico = medicoRepository.findById(idRestaurante)
                .orElseThrow(() -> new BusinessException("Medico não encontrado"));

        medicoRepository.delete(medico);
    }

    public MedicoDto toDto(Medico medico) {
        if (medico == null) {
            return null;
        }

        MedicoDto medicoDto = new MedicoDto();
        medicoDto.setId(medico.getId());
        medicoDto.setNome(medico.getNome());
        medicoDto.setNumeroRegistro(medico.getNumeroRegistro());
        medicoDto.setEspecializacao(medico.getEspecializacao());
        medicoDto.setEnderecos(medico.getEnderecos());
        medicoDto.setContatos(medico.getContatos());
        medicoDto.setConsultas(medico.getConsultas());

        return medicoDto;
    }

    public Medico toEntity(MedicoDto medicoDto) {
        if (medicoDto == null) {
            return null;
        }

        Medico medico = new Medico();
        medico.setId(medicoDto.getId());
        medico.setNome(medicoDto.getNome());
        medico.setNumeroRegistro(medicoDto.getNumeroRegistro());
        medico.setEspecializacao(medicoDto.getEspecializacao());
        medico.setEnderecos(medicoDto.getEnderecos());
        medico.setContatos(medicoDto.getContatos());
        medico.setConsultas(medicoDto.getConsultas());

        return medico;
    }

    private List<MedicoDto> toDtoList(List<Medico> medicos) {

        return medicos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

    }

}
