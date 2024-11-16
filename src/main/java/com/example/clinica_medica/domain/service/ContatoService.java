package com.example.clinica_medica.domain.service;

import com.example.clinica_medica.domain.dto.ContatoDto;
import com.example.clinica_medica.domain.dto.MedicoDto;
import com.example.clinica_medica.domain.dto.PacienteDto;
import com.example.clinica_medica.domain.exception.BusinessException;
import com.example.clinica_medica.domain.model.Contato;
import com.example.clinica_medica.domain.repository.ContatoRepository;
import com.example.clinica_medica.domain.repository.MedicoRepository;
import com.example.clinica_medica.domain.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public List<ContatoDto> buscarContatoPeloIdPaciente(Long idPaciente){
        try {

            List<Contato> listaContatoPaciente = contatoRepository.findByPacienteId(idPaciente);
            return toDtoList(listaContatoPaciente);

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel resgatar os contatos do medico");
        }
    }

    public List<ContatoDto> buscarContatoPeloIdMedico(Long idMedico){
        try {

            List<Contato> listaContatoMedico = contatoRepository.findByMedicoId(idMedico);
            return toDtoList(listaContatoMedico);

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel resgatar os contatos do medico");
        }
    }

    public void alterarContatoIdMedico(ContatoDto contatoDto, Long idMedico){
        try {

            contatoRepository.updateByIdMedico(idMedico, contatoDto.getEmail(), contatoDto.getTelefone());

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel resgatar os contatos do medico");
        }
    }

    public void alterarContatoIdPaciente(ContatoDto contatoDto, Long idPaciente){
        try {

            contatoRepository.updateByIdPaciente(idPaciente, contatoDto.getEmail(), contatoDto.getTelefone());

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel resgatar os contatos do medico");
        }
    }

    public void salvarContatoPaciente(PacienteDto pacienteDto) throws BusinessException {
        try {

            List<Contato> listaContatoPaciente = toEntityList(pacienteDto.getContatos());

            for (Contato contato : listaContatoPaciente){
                contato.setPaciente(pacienteRepository.findOneById(pacienteDto.getId()));
                contatoRepository.save(contato);
            }

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel salvar o contato do medico");
        }
    }

    public void salvarContatoMedico(MedicoDto medicoDto) throws BusinessException {
        try {

            List<Contato> listaContatoMedico = toEntityList(medicoDto.getContatos());

            for (Contato contato : listaContatoMedico){
                contato.setMedico(medicoRepository.findOneById(medicoDto.getId()));
                contatoRepository.save(contato);
            }

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel salvar o contato do medico");
        }
    }


    private ContatoDto toDto(Contato contato) {
        if (contato == null) {
            return null;
        }

        ContatoDto contatoDto = new ContatoDto();
        contatoDto.setId(contato.getId());
        contatoDto.setTelefone(contato.getTelefone());
        contatoDto.setEmail(contato.getEmail());

        return contatoDto;
    }

    private Contato toEntity(ContatoDto contatoDto) {
        if (contatoDto == null) {
            return null;
        }

        Contato contato = new Contato();
        contato.setId(contatoDto.getId());
        contato.setTelefone(contatoDto.getTelefone());
        contato.setEmail(contatoDto.getEmail());

        return contato;
    }

    private List<ContatoDto> toDtoList(List<Contato> contatos) {
        return contatos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private List<Contato> toEntityList(List<ContatoDto> contatos) {
        return contatos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
