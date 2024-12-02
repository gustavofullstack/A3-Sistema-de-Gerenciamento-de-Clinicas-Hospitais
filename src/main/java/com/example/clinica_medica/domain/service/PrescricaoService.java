package com.example.clinica_medica.domain.service;

import com.example.clinica_medica.domain.dto.ConsultaDto;
import com.example.clinica_medica.domain.dto.PacienteDto;
import com.example.clinica_medica.domain.dto.PrescricaoDto;
import com.example.clinica_medica.domain.exception.BusinessException;
import com.example.clinica_medica.domain.model.Prescricao;
import com.example.clinica_medica.domain.repository.PrescricaoRepository;
import jakarta.transaction.Transactional;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrescricaoService {
    
    @Autowired
    private PrescricaoRepository prescricaoRepository;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    @Lazy
    private ConsultaService consultaService;

    public PrescricaoDto buscarPrescricaoId(Long idPrescricao){
        try {

            Prescricao prescricao = prescricaoRepository.findOneById(idPrescricao);
            return toDto(prescricao);

        }catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<PrescricaoDto> buscarTodasPrescricao(){
        try {

            List<Prescricao> prescricaos = prescricaoRepository.findAll();
            return toDtoList(prescricaos);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void adicionarPrescricao(PrescricaoDto prescricaoDto){
        try {

            validaPrescricao(prescricaoDto);
            prescricaoRepository.save(toEntity(prescricaoDto));

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public void deletarPrescricao(Long idPrescricao) throws BusinessException{
        Prescricao prescricao = prescricaoRepository.findById(idPrescricao)
                .orElseThrow(() -> new BusinessException("Prescricao não encontrada"));

        validaPrescricao(toDto(prescricao));
        prescricaoRepository.delete(prescricao);
    }

    @Transactional
    public void alterarPrescricao(PrescricaoDto prescricaoDto) throws BusinessException {
        Prescricao prescricao = prescricaoRepository.findById(prescricaoDto.getId())
                .orElseThrow(() -> new BusinessException("Prescricao não encontrada"));

        validaPrescricao(prescricaoDto);
        if (!prescricao.getId().equals(prescricaoDto.getConsulta().getId())){
            throw new BusinessException("Consulta não pertence a essa prescrição");
        }

        prescricaoRepository.updateById(prescricaoDto.getConsulta().getId(), prescricaoDto.getId(),
                prescricaoDto.getDosagem(), prescricaoDto.getInstrucoesUso(), prescricaoDto.getMedicamento());
    }

    public PrescricaoDto toDto(Prescricao prescricao){
        try {
            if (prescricao == null) {
                throw new BusinessException("prescricao não encontrada");
            }

            PrescricaoDto prescricaoDto = new PrescricaoDto();

            prescricaoDto.setId(prescricao.getId());
            prescricaoDto.setConsulta(consultaService.toDtoSimplificado(prescricao.getConsulta()));
            prescricaoDto.setDosagem(prescricao.getDosagem());
            prescricaoDto.setMedicamento(prescricao.getMedicamento());
            prescricaoDto.setInstrucoesUso(prescricao.getInstrucoesUso());

            return prescricaoDto;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    private void validaPrescricao(PrescricaoDto prescricaoDto) throws BusinessException{
        try {

            ConsultaDto consulta = consultaService.buscarConsultaId(prescricaoDto.getConsulta().getId());
            PacienteDto paciente = pacienteService.buscarDadosPacientePeloId(prescricaoDto.getConsulta().getPaciente().getId());
            medicoService.buscarDadosMedicoPeloId(prescricaoDto.getConsulta().getMedico().getId());

            if(Optional.ofNullable(consulta).isEmpty()){
                throw new BusinessException("Consulta não encontrada para precrição");
            }

            for (PrescricaoDto prescricaoDtoVerify : consulta.getPrescricoes()){
                if(!prescricaoDtoVerify.getId().equals(prescricaoDto.getId())){
                    throw new BusinessException("Prescrição não pertence a essa consulta");
                }
            }

            if(!paciente.getId().equals(prescricaoDto.getConsulta().getPaciente().getId())){
                throw new BusinessException("Prescrição não pertence a esse paciente");
            }

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public Prescricao toEntity(PrescricaoDto prescricaoDto){
        try {
            if (prescricaoDto == null) {
                throw new BusinessException("prescricao não encontrada");
            }

            Prescricao prescricao = new Prescricao();

            prescricao.setId(prescricaoDto.getId());
            prescricao.setConsulta(consultaService.toEntitySimplificada(prescricaoDto.getConsulta()));
            prescricao.setDosagem(prescricaoDto.getDosagem());
            prescricao.setMedicamento(prescricaoDto.getMedicamento());
            prescricao.setInstrucoesUso(prescricaoDto.getInstrucoesUso());

            return prescricao;

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<PrescricaoDto> toDtoList(List<Prescricao> prescricaos) {

        return prescricaos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

    }

    public List<Prescricao> toEntityList(List<PrescricaoDto> prescricaos) {

        return prescricaos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

    }

}
