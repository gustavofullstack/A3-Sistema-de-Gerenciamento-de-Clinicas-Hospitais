package com.example.clinica_medica.domain.service;

import com.example.clinica_medica.domain.dto.ContatoDto;
import com.example.clinica_medica.domain.dto.EnderecoDto;
import com.example.clinica_medica.domain.dto.PacienteDto;
import com.example.clinica_medica.domain.dto.PacienteSimplificadoDto;
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

    public void alterarEnderecoPaciente(EnderecoDto enderecoDto, Long idPaciente){
        try {

            pacienteRepository.findById(idPaciente)
                    .orElseThrow(() -> new BusinessException("Paciente não encontrado"));

            enderecoService.alterarEnderecoIdPaciente(enderecoDto, idPaciente);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void adicionarEnderecoIdPaciente(List<EnderecoDto> enderecoaDto, Long idPaciente){
        try {

            enderecoService.adicionarEnderecoIdPaciente(enderecoaDto, idPaciente);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void alterarContatoIdPaciente(ContatoDto contatoDto, Long idPaciente){
        try {

            pacienteRepository.findById(idPaciente)
                    .orElseThrow(() -> new BusinessException("Paciente não encontrado"));

            contatoService.alterarContatoIdPaciente(contatoDto, idPaciente);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void adicionarContatoIdPaciente(List<ContatoDto> contatoDto, Long idPaciente){
        try {

            pacienteRepository.findById(idPaciente)
                    .orElseThrow(() -> new BusinessException("Paciente não encontrado"));

            contatoService.adicionarContatoIdPaciente(contatoDto, idPaciente);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public void deletarContatoPaciente(Long idPaciente, Long idContato) throws BusinessException{
        try {

            pacienteRepository.findById(idPaciente)
                    .orElseThrow(() -> new BusinessException("Paciente não encontrado"));

            contatoService.deletarContatoPaciente(idPaciente, idContato);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public void deletarEnderecoPaciente(Long idPaciente, Long idEndereco) throws BusinessException{
        try {

            pacienteRepository.findById(idPaciente)
                    .orElseThrow(() -> new BusinessException("Paciente não encontrado"));

            enderecoService.deletarEnderecoPaciente(idPaciente, idEndereco);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<ContatoDto> buscarContatosPeloIdPaciente(Long idPaciente){
        try {

            pacienteRepository.findById(idPaciente)
                    .orElseThrow(() -> new BusinessException("Paciente não encontrado"));

            List<ContatoDto> listaContato = contatoService.buscarContatoPeloIdPaciente(idPaciente);
            if(listaContato.isEmpty()){
                throw new BusinessException("Paciente não possui endereços");
            }

            return listaContato;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<EnderecoDto> buscarEnderecosPaciente(Long idPaciente){
        try {

            pacienteRepository.findById(idPaciente)
                    .orElseThrow(() -> new BusinessException("Paciente não encontrado"));

            List<EnderecoDto> listaEndereco = enderecoService.buscarEnderecoPeloIdPaciente(idPaciente);
            if(listaEndereco.isEmpty()){
                throw new BusinessException("Paciente não possui endereços");
            }

            return listaEndereco;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public PacienteDto buscarDadosPacientePeloId(Long idPaciente){

        Paciente paciente = pacienteRepository.findOneById(idPaciente);
        PacienteDto pacienteDto = toDto(paciente);

        List<EnderecoDto> listaEnderecoPaciente = enderecoService.buscarEnderecoPeloIdPaciente(idPaciente);
        List<ContatoDto> listaContatoPaciente = contatoService.buscarContatoPeloIdPaciente(idPaciente);

        pacienteDto.setEnderecos(listaEnderecoPaciente);
        pacienteDto.setContatos(listaContatoPaciente);

        return pacienteDto;
    }

    public List<PacienteSimplificadoDto> buscarTodosPacientes() throws BusinessException{
        try {

            List<Paciente> pacientes = pacienteRepository.findAll();
            return toDtoListSimplificado(pacientes);

        } catch (Exception e) {
            throw new BusinessException("Não foi possível listar todos os Pacientes.");
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

    public Paciente toEntitySimplificado(PacienteSimplificadoDto pacienteSimplificadoDto) {
        try {

            if (pacienteSimplificadoDto == null) {
                throw new BusinessException("paciente não encontrado");
            }

            Paciente paciente = new Paciente();

            paciente.setId(pacienteSimplificadoDto.getId());
            paciente.setNome(pacienteSimplificadoDto.getNome());
            paciente.setCpf(pacienteSimplificadoDto.getCpf());
            paciente.setDataNascimento(pacienteSimplificadoDto.getDataNascimento());
            paciente.setGenero(pacienteSimplificadoDto.getGenero());

            return paciente;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<PacienteSimplificadoDto> toDtoListSimplificado(List<Paciente> pacientes) {

        return pacientes.stream()
                .map(this::toDtoSimplificado)
                .collect(Collectors.toList());

    }

}
