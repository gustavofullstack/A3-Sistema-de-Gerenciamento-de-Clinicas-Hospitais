package com.example.clinica_medica.domain.service;

import com.example.clinica_medica.domain.dto.ContatoDto;
import com.example.clinica_medica.domain.dto.EnderecoDto;
import com.example.clinica_medica.domain.dto.MedicoDto;
import com.example.clinica_medica.domain.dto.MedicoSimplificadoDto;
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

    public List<ContatoDto> consultarContatosPeloIdMedico(Long idMedico){
        try {

            medicoRepository.findById(idMedico)
                    .orElseThrow(() -> new BusinessException("Medico não encontrado"));

            List<ContatoDto> listaContato = contatoService.buscarContatoPeloIdMedico(idMedico);
            if(listaContato.isEmpty()){
                throw new BusinessException("Medico não possui endereços");
            }

            return listaContato;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void alterarContatoIdMedico(ContatoDto contatoDto, Long idMedico){
        try {

            medicoRepository.findById(idMedico)
                    .orElseThrow(() -> new BusinessException("Medico não encontrado"));

            contatoService.alterarContatoIdMedico(contatoDto, idMedico);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void adicionarContatoIdMedico(List<ContatoDto> contatoDto, Long idMedico){
        try {

            contatoService.adicionarContatoIdMedico(contatoDto, idMedico);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public void deletarContatoMedico(Long idMedico, Long idContato) throws BusinessException{
        try {

            medicoRepository.findById(idMedico)
                    .orElseThrow(() -> new BusinessException("Medico não encontrado"));

            contatoService.deletarContatoMedico(idMedico, idContato);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<EnderecoDto> consultarEnderecoMedico(Long idMedico){
        try {

            medicoRepository.findById(idMedico)
                    .orElseThrow(() -> new BusinessException("Medico não encontrado"));

            List<EnderecoDto> listaEndereco = enderecoService.buscarEnderecoPeloIdMedico(idMedico);
            if(listaEndereco.isEmpty()){
                throw new BusinessException("Medico não possui endereços");
            }

            return listaEndereco;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void adicionarEnderecoIdMedico(List<EnderecoDto> enderecoaDto, Long idMedico){
        try {

            enderecoService.adicionarEnderecoIdMedico(enderecoaDto, idMedico);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public void deletarEnderecoMedico(Long idMedico, Long idEndereco) throws BusinessException{
        try {

            medicoRepository.findById(idMedico)
                    .orElseThrow(() -> new BusinessException("Medico não encontrado"));

            enderecoService.deletarEnderecoMedico(idMedico, idEndereco);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void alterarEnderecoMedico(EnderecoDto enderecoDto, Long idMedico){
        try {

            medicoRepository.findById(idMedico)
                    .orElseThrow(() -> new BusinessException("Medico não encontrado"));

            enderecoService.alterarEnderecoIdMedico(enderecoDto, idMedico);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public MedicoDto consultarDadosMedicoPeloId(Long id){

        Medico medico = medicoRepository.findOneById(id);
        MedicoDto medicoDto = toDto(medico);

        List<EnderecoDto> listaEnderecoMedico = enderecoService.buscarEnderecoPeloIdMedico(id);
        List<ContatoDto> listaContatoMedico = contatoService.buscarContatoPeloIdMedico(id);

        medicoDto.setEnderecos(listaEnderecoMedico);
        medicoDto.setContatos(listaContatoMedico);

        return medicoDto;
    }

    public List<MedicoSimplificadoDto> buscarTodosMedicos() throws BusinessException{
        List<Medico> medicos = medicoRepository.findAll();
        return toDtoListSimplificado(medicos);
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

        medicoRepository.updateById(medicoDto.getId(), medicoDto.getDataNascimento(), medicoDto.getCpf(), medicoDto.getNome(),
                medicoDto.getGenero().toString(), medicoDto.getNumeroRegistro(), medicoDto.getEspecializacao().toString());
    }

    @Transactional
    public void deletarMedico(Long idMedico) throws BusinessException{
        Medico medico = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new BusinessException("Medico não encontrado"));

        medicoRepository.delete(medico);
    }

    private MedicoDto toDto(Medico medico) {
        if (medico == null) {
            throw new BusinessException("Medico não encontrado");
        }

        MedicoDto medicoDto = new MedicoDto();
        medicoDto.setId(medico.getId());
        medicoDto.setCpf(medico.getCpf());
        medicoDto.setDataNascimento(medico.getDataNascimento());
        medicoDto.setGenero(medico.getGenero());
        medicoDto.setNome(medico.getNome());
        medicoDto.setNumeroRegistro(medico.getNumeroRegistro());
        medicoDto.setEspecializacao(medico.getEspecializacao());
        medicoDto.setConsultas(medico.getConsultas());

        return medicoDto;
    }

    private MedicoSimplificadoDto toDtoSimplificado(Medico medico) {
        if (medico == null) {
            throw new BusinessException("Medico não encontrado");
        }

        MedicoSimplificadoDto medicoDto = new MedicoSimplificadoDto();

        medicoDto.setId(medico.getId());
        medicoDto.setCpf(medico.getCpf());
        medicoDto.setDataNascimento(medico.getDataNascimento());
        medicoDto.setGenero(medico.getGenero());
        medicoDto.setNome(medico.getNome());
        medicoDto.setNumeroRegistro(medico.getNumeroRegistro());
        medicoDto.setEspecializacao(medico.getEspecializacao());

        return medicoDto;
    }

    private Medico toEntity(MedicoDto medicoDto) {
        if (medicoDto == null) {
            throw new BusinessException("Medico não encontrado");
        }

        Medico medico = new Medico();
        medico.setId(medicoDto.getId());
        medico.setCpf(medicoDto.getCpf());
        medico.setNome(medicoDto.getNome());
        medico.setGenero(medicoDto.getGenero());
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

    private List<MedicoSimplificadoDto> toDtoListSimplificado(List<Medico> medicos) {

        return medicos.stream()
                .map(this::toDtoSimplificado)
                .collect(Collectors.toList());

    }

}
