package com.example.clinica_medica.domain.service;

import com.example.clinica_medica.domain.dto.ContatoDto;
import com.example.clinica_medica.domain.dto.EnderecoDto;
import com.example.clinica_medica.domain.dto.PacienteDto;
import com.example.clinica_medica.domain.dto.PacienteSimplificadoDto;
import com.example.clinica_medica.domain.exception.BusinessException;
import com.example.clinica_medica.domain.model.Endereco;
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

    public void alterarEnderecoPaciente(EnderecoDto enderecoDto, Long idPaciente){
        try {

            enderecoService.alterarEnderecoIdPaciente(enderecoDto, idPaciente);

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel consultar os contatos do paciente");
        }
    }

    public List<ContatoDto> consultarContatosPeloIdPaciente(Long id){
        try {

            return contatoService.buscarContatoPeloIdPaciente(id);

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel consultar os contatos do paciente");
        }
    }

    public List<EnderecoDto> consultarEnderecosPaciente(Long id){
        try {

            return enderecoService.buscarEnderecoPeloIdPaciente(id);

        } catch (BusinessException e){
            throw new BusinessException("Não foi possivel consultar os endereços do paciente");
        }
    }

    public PacienteDto consultarDadosPacientePeloId(Long id){

        Paciente paciente = pacienteRepository.findOneById(id);
        PacienteDto pacienteDto = toDto(paciente);

        List<EnderecoDto> listaEnderecoPaciente = enderecoService.buscarEnderecoPeloIdPaciente(id);
        List<ContatoDto> listaContatoPaciente = contatoService.buscarContatoPeloIdPaciente(id);

        pacienteDto.setEnderecos(listaEnderecoPaciente);
        pacienteDto.setContatos(listaContatoPaciente);

        return pacienteDto;
    }

    public List<PacienteSimplificadoDto> buscarTodosPacientes() throws BusinessException{
        try {

            List<Paciente> pacientes = pacienteRepository.findAll();
            return toDtoListSimplificado(pacientes);

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
    public void alterarPaciente(PacienteSimplificadoDto pacienteDto) throws BusinessException {
        pacienteRepository.findById(pacienteDto.getId())
                .orElseThrow(() -> new BusinessException(String.format(
                        "Paciente com ID %d não encontrado para atualização.", pacienteDto.getId())));

        pacienteRepository.updateById(pacienteDto.getId(), pacienteDto.getDataNascimento(), pacienteDto.getCpf(),
                pacienteDto.getNome(), pacienteDto.getGenero().toString());
    }

    @Transactional
    public void deletarPaciente(Long idPaciente) throws BusinessException{
        Paciente paciente = pacienteRepository.findById(idPaciente)
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

    public PacienteSimplificadoDto toDtoSimplificado(Paciente paciente){
        try {
            if (paciente == null) {
                throw new BusinessException("paciente não encontrado");
            }

            PacienteSimplificadoDto pacienteDto = new PacienteSimplificadoDto();

            pacienteDto.setId(paciente.getId());
            pacienteDto.setNome(paciente.getNome());
            pacienteDto.setCpf(paciente.getCpf());
            pacienteDto.setDataNascimento(paciente.getDataNascimento());
            pacienteDto.setGenero(paciente.getGenero());

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

    private List<PacienteSimplificadoDto> toDtoListSimplificado(List<Paciente> pacientes) {

        return pacientes.stream()
                .map(this::toDtoSimplificado)
                .collect(Collectors.toList());

    }

}
