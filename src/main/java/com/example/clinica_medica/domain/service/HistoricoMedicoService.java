package com.example.clinica_medica.domain.service;

import com.example.clinica_medica.domain.dto.HistoricoMedicoDto;
import com.example.clinica_medica.domain.dto.HistoricoMedicoSimplificadoDto;
import com.example.clinica_medica.domain.dto.PacienteDto;
import com.example.clinica_medica.domain.exception.BusinessException;
import com.example.clinica_medica.domain.model.HistoricoMedico;
import com.example.clinica_medica.domain.repository.HistoricoMedicoRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoMedicoService {

    @Autowired
    private HistoricoMedicoRespository historicoMedicoRepository;

    @Autowired
    private PacienteService pacienteService;


    public HistoricoMedicoDto buscarHistoricoMedicoId(Long idHistoricoMedico){
        try {

            HistoricoMedico historicoMedico = historicoMedicoRepository.findOneById(idHistoricoMedico);
            return toDto(historicoMedico);

        }catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<HistoricoMedicoDto> buscarTodoHistoricoMedico(){
        try {

            List<HistoricoMedico> historicoMedicos = historicoMedicoRepository.findAll();
            return toDtoList(historicoMedicos);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void adicionarHistoricoMedico(HistoricoMedicoDto historicoMedicoDto){
        try {

            if (historicoMedicoDto == null || historicoMedicoDto.getPaciente() == null){
                throw new BusinessException("historico ou paciente não informado");
            }

            pacienteService.buscarDadosPacientePeloId(historicoMedicoDto.getPaciente().getId());
            historicoMedicoRepository.adicionarHistorico(historicoMedicoDto.getPaciente().getId(), historicoMedicoDto.getAlergias(),
                    historicoMedicoDto.getCondicao(), historicoMedicoDto.getMedicamentosEmUso(), historicoMedicoDto.getTratamento());

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public void deletarHistoricoMedico(Long idHistoricoMedico) throws BusinessException{
        HistoricoMedico historicoMedico = historicoMedicoRepository.findById(idHistoricoMedico)
                .orElseThrow(() -> new BusinessException("HistoricoMedico não encontrada"));

        validaHistoricoMedico(toDto(historicoMedico));
        historicoMedicoRepository.delete(historicoMedico);
    }

    @Transactional
    public void alterarHistoricoMedico(HistoricoMedicoDto historicoMedicoDto) throws BusinessException {
        validaHistoricoMedico(historicoMedicoDto);
        historicoMedicoRepository.updateById(historicoMedicoDto.getPaciente().getId(), historicoMedicoDto.getId(),
                historicoMedicoDto.getAlergias(), historicoMedicoDto.getCondicao(),
                historicoMedicoDto.getMedicamentosEmUso(), historicoMedicoDto.getTratamento());

    }

    public HistoricoMedicoDto toDto(HistoricoMedico historicoMedico){
        try {
            if (historicoMedico == null) {
                throw new BusinessException("historicoMedico não encontrada");
            }

            HistoricoMedicoDto historicoMedicoDto = new HistoricoMedicoDto();

            historicoMedicoDto.setId(historicoMedico.getId());
            historicoMedicoDto.setAlergias(historicoMedico.getAlergias());
            historicoMedicoDto.setCondicao(historicoMedico.getCondicao());
            historicoMedicoDto.setTratamento(historicoMedico.getTratamento());
            historicoMedicoDto.setMedicamentosEmUso(historicoMedico.getMedicamentosEmUso());
            historicoMedicoDto.setPaciente(pacienteService.toDtoSimplificado(historicoMedico.getPaciente()));

            return historicoMedicoDto;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public HistoricoMedicoSimplificadoDto toDtoSimplificado(HistoricoMedico historicoMedico){
        try {
            if (historicoMedico == null) {
                throw new BusinessException("historicoMedico não encontrada");
            }

            HistoricoMedicoSimplificadoDto historicoMedicoDto = new HistoricoMedicoSimplificadoDto();

            historicoMedicoDto.setId(historicoMedico.getId());
            historicoMedicoDto.setAlergias(historicoMedico.getAlergias());
            historicoMedicoDto.setCondicao(historicoMedico.getCondicao());
            historicoMedicoDto.setTratamento(historicoMedico.getTratamento());
            historicoMedicoDto.setMedicamentosEmUso(historicoMedico.getMedicamentosEmUso());

            return historicoMedicoDto;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    private void validaHistoricoMedico(HistoricoMedicoDto historicoMedicoDto) throws BusinessException{
        try {

            HistoricoMedico historicoMedico = historicoMedicoRepository.findById(historicoMedicoDto.getId())
                    .orElseThrow(() -> new BusinessException("HistoricoMedico não encontrada"));

            if(!historicoMedico.getPaciente().getId().equals(historicoMedicoDto.getPaciente().getId())){
                throw new BusinessException("Historico não pertence a esse paciente");
            }

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public HistoricoMedico toEntitySimplificado(HistoricoMedicoSimplificadoDto historicoMedicoDto){
        try {
            if (historicoMedicoDto == null) {
                throw new BusinessException("historicoMedico não encontrada");
            }

            HistoricoMedico historicoMedico = new HistoricoMedico();

            historicoMedico.setId(historicoMedicoDto.getId());
            historicoMedico.setAlergias(historicoMedicoDto.getAlergias());
            historicoMedico.setCondicao(historicoMedicoDto.getCondicao());
            historicoMedico.setTratamento(historicoMedicoDto.getTratamento());
            historicoMedico.setMedicamentosEmUso(historicoMedicoDto.getMedicamentosEmUso());

            return historicoMedico;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }


    public HistoricoMedico toEntity(HistoricoMedicoDto historicoMedicoDto){
        try {
            if (historicoMedicoDto == null) {
                throw new BusinessException("historicoMedico não encontrada");
            }

            HistoricoMedico historicoMedico = new HistoricoMedico();

            historicoMedico.setId(historicoMedicoDto.getId());
            historicoMedico.setAlergias(historicoMedicoDto.getAlergias());
            historicoMedico.setCondicao(historicoMedicoDto.getCondicao());
            historicoMedico.setTratamento(historicoMedicoDto.getTratamento());
            historicoMedico.setMedicamentosEmUso(historicoMedicoDto.getMedicamentosEmUso());
            historicoMedico.setPaciente(pacienteService.toEntitySimplificado(historicoMedicoDto.getPaciente()));

            return historicoMedico;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<HistoricoMedicoDto> toDtoList(List<HistoricoMedico> historicoMedicos) {

        return historicoMedicos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

    }

    public List<HistoricoMedico> toEntitySimplificadaList(List<HistoricoMedicoSimplificadoDto> historicoMedicos) {

        return historicoMedicos.stream()
                .map(this::toEntitySimplificado)
                .collect(Collectors.toList());

    }

    public List<HistoricoMedicoSimplificadoDto> toDtoSimplificadoList(List<HistoricoMedico> historicoMedicos) {

        return historicoMedicos.stream()
                .map(this::toDtoSimplificado)
                .collect(Collectors.toList());

    }


}
