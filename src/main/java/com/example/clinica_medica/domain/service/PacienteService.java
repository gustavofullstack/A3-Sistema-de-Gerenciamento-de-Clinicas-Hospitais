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

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private ContatoService contatoService;

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
    public void cadastroCompleto(PacienteDto pacienteDto) throws BusinessException{
        try {

            Paciente paciente = toEntity(pacienteDto);
            this.pacienteRepository.save(paciente);

            pacienteDto.setId(paciente.getId());
            enderecoService.salvarEnderecoPaciente(pacienteDto);
            contatoService.salvarContatoPaciente(pacienteDto);

        } catch (BusinessException e){
            throw  new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public void alterarPaciente(PacienteDto pacienteDto) throws BusinessException {
        pacienteRepository.findById(pacienteDto.getId())
                .orElseThrow(() -> new BusinessException(String.format("Paciente com ID %d não encontrado para atualização.", pacienteDto.getId())));

    }

    @Transactional
    public void deletarPaciente(Long idRestaurante) throws BusinessException{
        Paciente paciente = pacienteRepository.findById(idRestaurante)
                .orElseThrow(() -> new BusinessException("Paciente não encontrado"));

        pacienteRepository.delete(paciente);
    }

    public PacienteDto toDto(Paciente paciente){
        try {
            if (paciente == null) {
                throw new BusinessException("paciente não encontrado");
            }

            PacienteDto pacienteDto = new PacienteDto();

            pacienteDto.setId(paciente.getId());
            pacienteDto.setNome(paciente.getNome());
            pacienteDto.setCpf(paciente.getCpf());
            pacienteDto.setDataNascimento(paciente.getDataNascimento());
            pacienteDto.setGenero(paciente.getGenero());
            pacienteDto.setConsultas(paciente.getConsultas());
            pacienteDto.setHistoricoMedico(paciente.getHistoricoMedico());

            return pacienteDto;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public Paciente toEntity(PacienteDto pacienteDto) {
        try {

            if (pacienteDto == null) {
                throw new BusinessException("paciente não encontrado");
            }

            Paciente paciente = new Paciente();

            paciente.setNome(pacienteDto.getNome());
            paciente.setCpf(pacienteDto.getCpf());
            paciente.setDataNascimento(pacienteDto.getDataNascimento());
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
