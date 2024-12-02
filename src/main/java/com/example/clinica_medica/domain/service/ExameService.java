package com.example.clinica_medica.domain.service;

import com.example.clinica_medica.domain.dto.ConsultaDto;
import com.example.clinica_medica.domain.dto.ExameDto;
import com.example.clinica_medica.domain.dto.PacienteDto;
import com.example.clinica_medica.domain.exception.BusinessException;
import com.example.clinica_medica.domain.model.Exame;
import com.example.clinica_medica.domain.model.Prescricao;
import com.example.clinica_medica.domain.repository.ExameRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExameService {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ExameRespository exameRepository;

    public ExameDto buscarExameId(Long idExame){
        try {

            Exame exame = exameRepository.findOneById(idExame);
            return toDto(exame);

        }catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<ExameDto> buscarTodasExame(){
        try {

            List<Exame> exames = exameRepository.findAll();
            return toDtoList(exames);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void adicionarExame(ExameDto exameDto){
        try {

            validaExame(exameDto);
            exameRepository.save(toEntity(exameDto));

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public void deletarExame(Long idExame) throws BusinessException{
        Exame exame = exameRepository.findById(idExame)
                .orElseThrow(() -> new BusinessException("Exame não encontrado"));

        validaExame(toDto(exame));
        exameRepository.delete(exame);
    }

    @Transactional
    public void alterarExame(ExameDto exameDto) throws BusinessException {
        Exame exame = exameRepository.findById(exameDto.getId())
                .orElseThrow(() -> new BusinessException("Exame não encontrado"));

        validaExame(exameDto);
        if (!exame.getId().equals(exameDto.getConsulta().getId())){
            throw new BusinessException("Consulta não pertence a esse exame");
        }

        exameRepository.updateById(exame.getId(), exameDto.getConsulta().getId(),
                exameDto.getDataExame(), exameDto.getNomeExame(), exameDto.getResultado());
    }

    public ExameDto toDto(Exame exame){
        try {
            if (exame == null) {
                throw new BusinessException("exame não encontrado");
            }

            ExameDto exameDto = new ExameDto();

            exameDto.setId(exame.getId());
            exameDto.setDataExame(exame.getDataExame());
            exameDto.setNomeExame(exame.getNomeExame());
            exameDto.setConsulta(consultaService.toDtoSimplificado(exame.getConsulta()));
            exameDto.setResultado(exame.getResultado());

            return exameDto;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    private void validaExame(ExameDto exameDto) throws BusinessException{
        try {

            ConsultaDto consulta = consultaService.buscarConsultaId(exameDto.getConsulta().getId());
            PacienteDto paciente = pacienteService.buscarDadosPacientePeloId(exameDto.getConsulta().getPaciente().getId());
            medicoService.buscarDadosMedicoPeloId(exameDto.getConsulta().getMedico().getId());

            if(Optional.ofNullable(consulta).isEmpty()){
                throw new BusinessException("Consulta não encontrada para precrição");
            }

            if(!paciente.getId().equals(exameDto.getConsulta().getPaciente().getId())){
                throw new BusinessException("Exame não pertence a esse paciente");
            }

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public Exame toEntity(ExameDto exameDto){
        try {
            if (exameDto == null) {
                throw new BusinessException("exame não encontrada");
            }

            Exame exame = new Exame();

            exame.setId(exameDto.getId());
            exame.setDataExame(exameDto.getDataExame());
            exame.setNomeExame(exameDto.getNomeExame());
            exame.setConsulta(consultaService.toEntitySimplificada(exameDto.getConsulta()));
            exame.setResultado(exameDto.getResultado());

            return exame;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<ExameDto> toDtoList(List<Exame> exames) {

        return exames.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

    }

    public List<Exame> toEntityList(List<ExameDto> exames) {

        return exames.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

    }
    
}
