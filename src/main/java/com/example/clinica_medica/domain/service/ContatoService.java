package com.example.clinica_medica.domain.service;

import com.example.clinica_medica.domain.dto.ContatoDto;
import com.example.clinica_medica.domain.dto.MedicoDto;
import com.example.clinica_medica.domain.dto.PacienteDto;
import com.example.clinica_medica.domain.exception.BusinessException;
import com.example.clinica_medica.domain.model.Contato;
import com.example.clinica_medica.domain.model.Medico;
import com.example.clinica_medica.domain.model.Paciente;
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

    public void salvarContatoPaciente(PacienteDto pacienteDto) throws BusinessException {
        try {

            List<ContatoDto> listaContato = relacionaContatoMedico(pacienteDto);
            List<Contato> listaContatoPaciente = toEntityList(listaContato);

            for (Contato contato : listaContatoPaciente){
                contatoRepository.save(contato);
            }

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel salvar o contato do medico");
        }
    }

    public void salvarContatoMedico(MedicoDto medicoDto) throws BusinessException {
        try {

            List<ContatoDto> listaContato = relacionaContatoMedico(medicoDto);
            List<Contato> listaContatoMedico = toEntityList(listaContato);

            for (Contato contato : listaContatoMedico){
                contatoRepository.save(contato);
            }

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel salvar o contato do medico");
        }
    }

    private List<ContatoDto> relacionaContatoMedico(MedicoDto medicoDto){

        List<ContatoDto> listaContato = medicoDto.getContatos();
        for(ContatoDto contatoDto : listaContato){
            contatoDto.setMedico(toMedicoDto(medicoRepository.findOneById(medicoDto.getId())));
        }
        return listaContato;
    }

    private List<ContatoDto> relacionaContatoMedico(PacienteDto pacienteDto){

        List<ContatoDto> listaContato = pacienteDto.getContatos();
        for(ContatoDto contatoDto : listaContato){
            contatoDto.setPaciente(toPacienteDto(pacienteRepository.findOneById(pacienteDto.getId())));
        }
        return listaContato;

    }

    private ContatoDto toDto(Contato contato) {
        if (contato == null) {
            return null;
        }

        ContatoDto contatoDto = new ContatoDto();
        contatoDto.setId(contato.getId());
        contatoDto.setTelefone(contato.getTelefone());
        contatoDto.setEmail(contato.getEmail());
        contatoDto.setPaciente(toPacienteDto(contato.getPaciente()));
        contatoDto.setMedico(toMedicoDto(contato.getMedico()));

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
        contato.setPaciente(toPacienteEntity(contatoDto.getPaciente()));
        contato.setMedico(toMedicoEntity(contatoDto.getMedico()));

        return contato;
    }

    private Paciente toPacienteEntity(PacienteDto pacienteDto) {
        if (pacienteDto == null) {
            return null;
        }
        Paciente paciente = new Paciente();
        paciente.setId(pacienteDto.getId());
        paciente.setNome(pacienteDto.getNome());
        return paciente;
    }

    private Medico toMedicoEntity(MedicoDto medicoDto) {
        if (medicoDto == null) {
            return null;
        }
        Medico medico = new Medico();
        medico.setId(medicoDto.getId());
        medico.setNome(medicoDto.getNome());
        return medico;
    }

    private PacienteDto toPacienteDto(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        PacienteDto pacienteDto = new PacienteDto();
        pacienteDto.setId(paciente.getId());
        pacienteDto.setNome(paciente.getNome());
        return pacienteDto;
    }

    private MedicoDto toMedicoDto(Medico medico) {
        if (medico == null) {
            return null;
        }
        MedicoDto medicoDto = new MedicoDto();
        medicoDto.setId(medico.getId());
        medicoDto.setNome(medico.getNome());
        return medicoDto;
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
