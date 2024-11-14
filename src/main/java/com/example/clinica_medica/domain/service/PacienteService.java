package com.example.clinica_medica.domain.service;

import com.example.clinica_medica.domain.dto.PacienteDto;
import com.example.clinica_medica.domain.exception.BusinessException;
import com.example.clinica_medica.domain.model.Paciente;
import com.example.clinica_medica.domain.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public PacienteDto consultarDadosPacientePeloId(Long id){

        Paciente paciente = pacienteRepository.findOneById(id);
        PacienteDto pacienteDto = toDto(paciente);
        return pacienteDto;
    }

    public List<PacienteDto> buscarTodosPacientes() throws BusinessException{
        try {

            List<Paciente> pacientes = pacienteRepository.findAll();
            return toDtoList(pacientes);

        } catch (Exception e) {
            throw new BusinessException("Não foi possível listar todos os restaurantes.");
        }
    }

    @Transactional
    public void salvarPaciente(PacienteDto pacienteDto) throws BusinessException{
        Paciente paciente = toEntity(pacienteDto);
        this.pacienteRepository.save(paciente);
    }

    @Transactional
    public void alterarPaciente(PacienteDto pacienteDto) throws BusinessException {
        pacienteRepository.findById(pacienteDto.getId())
                .orElseThrow(() -> new BusinessException(String.format("Paciente com ID %d não encontrado para atualização.", pacienteDto.getId())));

        pacienteRepository.updateById(
                pacienteDto.getId(),
                pacienteDto.getNome(),
                pacienteDto.getCpf(),
                pacienteDto.getDataNascimento(),
                pacienteDto.getGenero().toString(),
                pacienteDto.getEndereco().getRua(),
                pacienteDto.getEndereco().getCep(),
                pacienteDto.getEndereco().getBairro(),
                pacienteDto.getEndereco().getNumero(),
                pacienteDto.getEndereco().getCidade(),
                pacienteDto.getEndereco().getComplemento(),
                pacienteDto.getContato().getTelefone(),
                pacienteDto.getContato().getEmail()
        );
    }

    @Transactional
    public void deletarPaciente(Long idRestaurante) throws BusinessException{
        Paciente paciente = pacienteRepository.findById(idRestaurante)
                .orElseThrow(() -> new BusinessException("Paciente não encontrado"));

        pacienteRepository.delete(paciente);
    }

    private PacienteDto toDto(Paciente paciente){
        try {
            if (paciente == null) {
                throw new BusinessException("paciente não encontrado");
            }

            PacienteDto pacienteDto = new PacienteDto();

            pacienteDto.setId(paciente.getId());
            pacienteDto.setNome(paciente.getNome());
            pacienteDto.setCpf(paciente.getCpf());
            pacienteDto.setDataNascimento(paciente.getDataNascimento());
            pacienteDto.setEndereco(paciente.getEndereco());
            pacienteDto.setContato(paciente.getContato());
            pacienteDto.setConsultas(paciente.getConsultas());
            pacienteDto.setHistoricoMedico(paciente.getHistoricoMedico());
            pacienteDto.setGenero(paciente.getGenero());


            return pacienteDto;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    private Paciente toEntity(PacienteDto pacienteDto) {
        try {

            if (pacienteDto == null) {
                throw new BusinessException("paciente não encontrado");
            }

            Paciente paciente = new Paciente();

            paciente.setNome(pacienteDto.getNome());
            paciente.setCpf(pacienteDto.getCpf());
            paciente.setDataNascimento(pacienteDto.getDataNascimento());
            paciente.setEndereco(pacienteDto.getEndereco());
            paciente.setContato(pacienteDto.getContato());
            paciente.setConsultas(pacienteDto.getConsultas());
            paciente.setHistoricoMedico(pacienteDto.getHistoricoMedico());
            paciente.setGenero(pacienteDto.getGenero());

            return paciente;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    private List<PacienteDto> toDtoList(List<Paciente> pacientes) {

        return pacientes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

    }

}
