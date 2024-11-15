package com.example.clinica_medica.domain.service;

import com.example.clinica_medica.domain.dto.EnderecoDto;
import com.example.clinica_medica.domain.dto.MedicoDto;
import com.example.clinica_medica.domain.dto.PacienteDto;
import com.example.clinica_medica.domain.exception.BusinessException;
import com.example.clinica_medica.domain.model.Endereco;
import com.example.clinica_medica.domain.model.Medico;
import com.example.clinica_medica.domain.model.Paciente;
import com.example.clinica_medica.domain.repository.EnderecoRepository;
import com.example.clinica_medica.domain.repository.MedicoRepository;
import com.example.clinica_medica.domain.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public void salvarEndercoMedico(MedicoDto medicoDto) throws BusinessException {
        try {

            List<EnderecoDto> listaEndereco = relacionaEnderecoMedico(medicoDto);
            List<Endereco> listaEnderecoMedico = toEntityList(listaEndereco);

            for (Endereco endereco : listaEnderecoMedico){
                enderecoRepository.save(endereco);
            }

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel salvar o endereço do medico");
            }
    }

    public void salvarEnderecoPaciente(PacienteDto pacienteDto) throws BusinessException {
        try {

            List<EnderecoDto> listaEndereco = relacionaEnderecoPaciente(pacienteDto);
            List<Endereco> listaEnderecoMedico = toEntityList(listaEndereco);

            for (Endereco endereco : listaEnderecoMedico){
                enderecoRepository.save(endereco);
            }

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel salvar o endereço do medico");
        }
    }

    private List<EnderecoDto> relacionaEnderecoMedico(MedicoDto medicoDto){

        List<EnderecoDto> listaEndereco = medicoDto.getEnderecos();
        for(EnderecoDto enderecoDto : listaEndereco){
            enderecoDto.setMedico(toMedicoDto(medicoRepository.findOneById(medicoDto.getId())));
        }

        return listaEndereco;

    }

    private List<EnderecoDto> relacionaEnderecoPaciente(PacienteDto pacienteDto){

        List<EnderecoDto> listaEndereco = pacienteDto.getEnderecos();
        for(EnderecoDto enderecoDto : listaEndereco){
            enderecoDto.setPaciente(toPacienteDto(pacienteRepository.findOneById(pacienteDto.getId())));
        }

        return listaEndereco;

    }

    private EnderecoDto toDto(Endereco endereco) {
        if (endereco == null) {
            return null;
        }

        EnderecoDto enderecoDto = new EnderecoDto();

        enderecoDto.setId(endereco.getId());
        enderecoDto.setRua(endereco.getRua());
        enderecoDto.setCep(endereco.getCep());
        enderecoDto.setBairro(endereco.getBairro());
        enderecoDto.setNumero(endereco.getNumero());
        enderecoDto.setCidade(endereco.getCidade());
        enderecoDto.setComplemento(endereco.getComplemento());

        enderecoDto.setPaciente(toPacienteDto(endereco.getPaciente()));
        enderecoDto.setMedico(toMedicoDto(endereco.getMedico()));

        return enderecoDto;
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
        medicoDto.setNumeroRegistro(medico.getNumeroRegistro());
        return medicoDto;
    }

    private Endereco toEntity(EnderecoDto enderecoDto) {
        if (enderecoDto == null) {
            return null;
        }

        Endereco endereco = new Endereco();

        endereco.setId(enderecoDto.getId());
        endereco.setRua(enderecoDto.getRua());
        endereco.setCep(enderecoDto.getCep());
        endereco.setBairro(enderecoDto.getBairro());
        endereco.setNumero(enderecoDto.getNumero());
        endereco.setCidade(enderecoDto.getCidade());
        endereco.setComplemento(enderecoDto.getComplemento());

        endereco.setPaciente(toPacienteEntity(enderecoDto.getPaciente()));
        endereco.setMedico(toMedicoEntity(enderecoDto.getMedico()));

        return endereco;
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
        medico.setNumeroRegistro(medicoDto.getNumeroRegistro());
        return medico;
    }


    private List<EnderecoDto> toDtoList(List<Endereco> enderecos) {
        return enderecos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private List<Endereco> toEntityList(List<EnderecoDto> enderecos) {
        return enderecos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}
