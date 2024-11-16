package com.example.clinica_medica.domain.service;

import com.example.clinica_medica.domain.dto.ContatoDto;
import com.example.clinica_medica.domain.dto.EnderecoDto;
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

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private ContatoService contatoService;

    public MedicoDto consultarDadosMedicoPeloId(Long id){

        Medico medico = medicoRepository.findOneById(id);
        MedicoDto medicoDto = toDto(medico);

        List<EnderecoDto> listaEnderecoMedico = enderecoService.buscarEnderecoPeloIdMedico(id);
        List<ContatoDto> listaContatoMedico = contatoService.buscarContatoPeloIdMedico(id);

        medicoDto.setEnderecos(listaEnderecoMedico);
        medicoDto.setContatos(listaContatoMedico);


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
    public void cadastroCompleto(MedicoDto medicoDto) throws BusinessException{
        try {

            Medico medico = toEntity(medicoDto);
            this.medicoRepository.save(medico);

            medicoDto.setId(medico.getId());
            enderecoService.salvarEndercoMedico(medicoDto);
            contatoService.salvarContatoMedico(medicoDto);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
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

    private MedicoDto toDto(Medico medico) {
        if (medico == null) {
            return null;
        }

        MedicoDto medicoDto = new MedicoDto();
        medicoDto.setId(medico.getId());
        medicoDto.setCpf(medico.getCpf());
        medicoDto.setDataNascimento(medico.getDataNascimento());
        medicoDto.setNome(medico.getNome());
        medicoDto.setNumeroRegistro(medico.getNumeroRegistro());
        medicoDto.setEspecializacao(medico.getEspecializacao());
        medicoDto.setConsultas(medico.getConsultas());

        return medicoDto;
    }

    private Medico toEntity(MedicoDto medicoDto) {
        if (medicoDto == null) {
            return null;
        }

        Medico medico = new Medico();
        medico.setId(medicoDto.getId());
        medico.setCpf(medicoDto.getCpf());
        medico.setNome(medicoDto.getNome());
        medico.setDataNascimento(medicoDto.getDataNascimento());
        medico.setNumeroRegistro(medicoDto.getNumeroRegistro());
        medico.setEspecializacao(medicoDto.getEspecializacao());

        return medico;
    }

    private List<MedicoDto> toDtoList(List<Medico> medicos) {

        return medicos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

    }

}
