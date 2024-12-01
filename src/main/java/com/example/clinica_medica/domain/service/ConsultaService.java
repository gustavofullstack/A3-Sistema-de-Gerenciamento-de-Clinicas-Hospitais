package com.example.clinica_medica.domain.service;

import com.example.clinica_medica.domain.dto.*;
import com.example.clinica_medica.domain.exception.BusinessException;
import com.example.clinica_medica.domain.model.Consulta;
import com.example.clinica_medica.domain.model.Endereco;
import com.example.clinica_medica.domain.model.Medico;
import com.example.clinica_medica.domain.repository.ConsultaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    public ConsultaDto buscarConsultaId(Long idConsulta){
        try {

            Consulta consulta = consultaRepository.findOneById(idConsulta);
            return toDto(consulta);

        }catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<ConsultaSimplificadaDto> buscarTodasConsultas(){
        try {

            List<Consulta> consultas = consultaRepository.findAll();
            return toDtoListSimplificado(consultas);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void agendarConsulta(ConsultaSimplificadaDto consultaSimplificadaDto){
        try {

            validaConsultaSimplificada(consultaSimplificadaDto);
            Consulta consulta = toEntitySimplificada(consultaSimplificadaDto);

            consultaRepository.save(consulta);


        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void validaConsultaSimplificada(ConsultaSimplificadaDto consultaSimplificadaDto) throws BusinessException{
        try {

            if (Optional.ofNullable(consultaSimplificadaDto).isEmpty()){
                throw new BusinessException("Consulta não pode ser vazia");
            }

            medicoService.buscarDadosMedicoPeloId(consultaSimplificadaDto.getMedico().getId());
            pacienteService.buscarDadosPacientePeloId(consultaSimplificadaDto.getPaciente().getId());

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public void deletarConsulta(Long idConsulta) throws BusinessException{
        Consulta consulta = consultaRepository.findById(idConsulta)
                .orElseThrow(() -> new BusinessException("Consulta não encontrada"));

        consultaRepository.delete(consulta);
    }

    @Transactional
    public void alterarConsulta(ConsultaSimplificadaDto consultaSimplificadaDto) throws BusinessException {
        validaConsultaSimplificada(consultaSimplificadaDto);
        consultaRepository.findById(consultaSimplificadaDto.getId())
                .orElseThrow(() -> new BusinessException("Consulta não encontrada"));

        consultaRepository.updateById(consultaSimplificadaDto.getId(),consultaSimplificadaDto.getPaciente().getId(),
                consultaSimplificadaDto.getMedico().getId(), consultaSimplificadaDto.getDataHora(),
                consultaSimplificadaDto.getMotivoConsulta());
    }

    public ConsultaDto toDto(Consulta consulta){
        try {
            if (consulta == null) {
                throw new BusinessException("consulta não encontrada");
            }

            ConsultaDto consultaDto = new ConsultaDto();

            consultaDto.setId(consulta.getId());
            consultaDto.setPaciente(pacienteService.toDtoSimplificado(consulta.getPaciente()));
            consultaDto.setMedico(medicoService.toDtoSimplificado(consulta.getMedico()));
            consultaDto.setMotivoConsulta(consulta.getMotivoConsulta());
            consultaDto.setDataHora(consulta.getDataHora());
            consultaDto.setExames(consulta.getExames());
            consultaDto.setObservacoesMedicas(consulta.getObservacoesMedicas());
            consultaDto.setPrescricoes(consulta.getPrescricoes());

            return consultaDto;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public Consulta toEntity(ConsultaDto consultaDto){
        try {
            if (consultaDto == null) {
                throw new BusinessException("consulta não encontrada");
            }

            Consulta consulta = new Consulta();

            consulta.setId(consultaDto.getId());
            consulta.setPaciente(pacienteService.toEntitySimplificado(consultaDto.getPaciente()));
            consulta.setMedico(medicoService.toEntitySimplificado(consultaDto.getMedico()));
            consulta.setMotivoConsulta(consultaDto.getMotivoConsulta());
            consulta.setDataHora(consultaDto.getDataHora());
            consulta.setExames(consultaDto.getExames());
            consulta.setObservacoesMedicas(consultaDto.getObservacoesMedicas());
            consulta.setPrescricoes(consultaDto.getPrescricoes());

            return consulta;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public ConsultaSimplificadaDto toDtoSimplificado(Consulta consulta){
        try {
            if (consulta == null) {
                throw new BusinessException("consulta não encontrada");
            }

            ConsultaSimplificadaDto consultaSimplificadaDto = new ConsultaSimplificadaDto();

            consultaSimplificadaDto.setId(consulta.getId());
            consultaSimplificadaDto.setPaciente(pacienteService.toDtoSimplificado(consulta.getPaciente()));
            consultaSimplificadaDto.setMedico(medicoService.toDtoSimplificado(consulta.getMedico()));
            consultaSimplificadaDto.setMotivoConsulta(consulta.getMotivoConsulta());
            consultaSimplificadaDto.setDataHora(consulta.getDataHora());

            return consultaSimplificadaDto;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public Consulta toEntitySimplificada(ConsultaSimplificadaDto consultaSimplificadaDto){
        try {
            if (consultaSimplificadaDto == null) {
                throw new BusinessException("consulta não encontrada");
            }

            Consulta consulta = new Consulta();

            consulta.setPaciente(pacienteService.toEntitySimplificado(consultaSimplificadaDto.getPaciente()));
            consulta.setMedico(medicoService.toEntitySimplificado(consultaSimplificadaDto.getMedico()));
            consulta.setMotivoConsulta(consultaSimplificadaDto.getMotivoConsulta());
            consulta.setDataHora(consultaSimplificadaDto.getDataHora());

            return consulta;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<ConsultaSimplificadaDto> toDtoListSimplificado(List<Consulta> consultas) {

        return consultas.stream()
                .map(this::toDtoSimplificado)
                .collect(Collectors.toList());

    }

}
