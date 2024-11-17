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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public void alterarEnderecoIdMedico(EnderecoDto enderecoDto, Long idMedico) throws BusinessException {
        try {

            enderecoRepository.updateByMedicoId(idMedico, enderecoDto.getId(), enderecoDto.getBairro(), enderecoDto.getCep(),
                    enderecoDto.getCidade(), enderecoDto.getComplemento(), enderecoDto.getNumero(), enderecoDto.getRua());

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel alterar o endereço do medico");
        }
    }

    public void alterarEnderecoIdPaciente(EnderecoDto enderecoDto, Long idPaciente) throws BusinessException {
        try {

            enderecoRepository.updateByPacienteId(idPaciente, enderecoDto.getId(), enderecoDto.getBairro(), enderecoDto.getCep(),
                    enderecoDto.getCidade(), enderecoDto.getComplemento(), enderecoDto.getNumero(), enderecoDto.getRua());

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel alterar o endereço do medico");
        }
    }

    @Transactional
    public void deletarEnderecoMedico(Long idMedico, Long idEndereco) throws BusinessException{
        try {

            enderecoRepository.deleteByIdAndMedicoId(idEndereco, idMedico);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<EnderecoDto> buscarEnderecoPeloIdPaciente(Long idPaciente){
        try {

            List<Endereco> enderecoPaciente = enderecoRepository.findByPacienteId(idPaciente);
            return toDtoList(enderecoPaciente);

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel resgatar os endereços do medico");
        }
    }

    @Transactional
    public void deletarEnderecoPaciente(Long idPaciente, Long idEndereco) throws BusinessException{
        try {

            enderecoRepository.deleteByIdAndPacienteId(idEndereco, idPaciente);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<EnderecoDto> buscarEnderecoPeloIdMedico(Long idMedico){
        try {

            List<Endereco> enderecoMedico = enderecoRepository.findByMedicoId(idMedico);
            return toDtoList(enderecoMedico);

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel resgatar os endereços do medico");
        }
    }

    public void salvarEndercoMedico(MedicoDto medicoDto) throws BusinessException {
        try {

            List<Endereco> listaEnderecoMedico = toEntityList(medicoDto.getEnderecos());

            for (Endereco endereco : listaEnderecoMedico){
                endereco.setMedico(medicoRepository.findOneById(medicoDto.getId()));
                enderecoRepository.save(endereco);
            }

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel salvar o endereço do medico");
        }
    }

    public void adicionarEnderecoIdMedico(List<EnderecoDto> enderecosDto, Long idPaciente) throws BusinessException {
        try {

            List<Endereco> enderecosPaciente = toEntityList(enderecosDto);

            Medico medico = medicoRepository.findOneById(idPaciente);
            if (Optional.ofNullable(medico).isEmpty()){
                throw new BusinessException("Medico não existente");
            }

            for (Endereco endereco : enderecosPaciente){
                endereco.setMedico(medico);
                enderecoRepository.save(endereco);
            }

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void adicionarEnderecoIdPaciente(List<EnderecoDto> enderecosDto, Long idPaciente) throws BusinessException {
        try {

            List<Endereco> enderecosPaciente = toEntityList(enderecosDto);

            Paciente paciente = pacienteRepository.findOneById(idPaciente);
            if (Optional.ofNullable(paciente).isEmpty()){
                throw new BusinessException("Paciente não existente");
            }

            for (Endereco endereco : enderecosPaciente){
                endereco.setPaciente(paciente);
                enderecoRepository.save(endereco);
            }

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void salvarEnderecoPaciente(PacienteDto pacienteDto) throws BusinessException {
        try {

            List<Endereco> listaEnderecoMedico = toEntityList(pacienteDto.getEnderecos());

            for (Endereco endereco : listaEnderecoMedico){
                endereco.setPaciente(pacienteRepository.findOneById(pacienteDto.getId()));
                enderecoRepository.save(endereco);
            }

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel salvar o endereço do medico");
        }
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

        return enderecoDto;
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

        return endereco;
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
