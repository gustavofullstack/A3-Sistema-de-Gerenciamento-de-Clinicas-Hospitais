package com.example.clinica_medica.controller;

import com.example.clinica_medica.domain.dto.ContatoDto;
import com.example.clinica_medica.domain.dto.EnderecoDto;
import com.example.clinica_medica.domain.dto.MedicoDto;
import com.example.clinica_medica.domain.dto.MedicoSimplificadoDto;
import com.example.clinica_medica.domain.exception.BusinessException;
import com.example.clinica_medica.domain.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/buscar-todos")
    public ResponseEntity<List<Object>> buscarTodosMedicos(){
        try{

            List<MedicoSimplificadoDto> medicos = medicoService.buscarTodosMedicos();

            if (medicos.isEmpty()){
                throw new Exception("Não existe medicos!");
            }

            return ResponseEntity.ok(Collections.singletonList(medicos));

        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<Object> consultarDadosMedicoPeloId(@PathVariable Long id) {
        try {

            MedicoDto medico = medicoService.consultarDadosMedicoPeloId(id);

            if(Optional.ofNullable(medico).isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok().body(medico);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/cadastro-completo")
    public ResponseEntity<Object> cadastroCompleto(@RequestBody MedicoDto medicoDto){
        try {

            medicoService.cadastroCompleto(medicoDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PutMapping("/alterar-id/{id}")
    public ResponseEntity<Object> updateMedico(@RequestBody MedicoDto medicoDto, @PathVariable("id") Long id){
        try {

            medicoDto.setId(id);
            medicoService.alterarMedico(medicoDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar-id/{id}")
    public ResponseEntity<Object> deletarMedico(@PathVariable("id") Long id){
        try {

            medicoService.deletarMedico(id);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/buscar-enderecos/{idMedico}")
    public ResponseEntity<List<Object>> consultarEnderecoPeloIdMedico(@PathVariable("idMedico") Long idMedico){
        try {

            List<EnderecoDto> enderecoMedico = medicoService.consultarEnderecoMedico(idMedico);

            if(enderecoMedico.isEmpty()){
                throw new BusinessException("Endereço não encontrado para o medico");
            }
            return ResponseEntity.ok().body(Collections.singletonList(enderecoMedico));

        } catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @PostMapping("/adicionar-endereco/{idMedico}")
    public ResponseEntity<Object> adicionarEnderecoPaciente(@RequestBody List<EnderecoDto> enderecosDto, @PathVariable("idMedico") Long idMedico){
        try {

            medicoService.adicionarEnderecoIdMedico(enderecosDto, idMedico);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
    }

    @PutMapping("/alterar-endereco/{id}")
    public ResponseEntity<Object> updateEnderecoMedico(@RequestBody EnderecoDto enderecoDto, @PathVariable("id") Long idMedico){
        try {

            medicoService.alterarEnderecoMedico(enderecoDto, idMedico);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar-endereco/{idMedico}/{idEndereco}")
    public ResponseEntity<Object> deletarMedico(@PathVariable("idMedico") Long idMedico, @PathVariable("idEndereco") Long idEndereco){
        try {

            medicoService.deletarEnderecoMedico(idMedico, idEndereco);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/buscar-contatos/{idMedico}")
    public ResponseEntity<List<Object>> consultarContatosPeloIdPaciente(@PathVariable("idMedico") Long idMedico){
        try {

            List<ContatoDto> contatosPaciente = medicoService.consultarContatosPeloIdMedico(idMedico);

            if(contatosPaciente.isEmpty()){
                throw new BusinessException("Nenhum contato encontrado para o medico");
            }
            return ResponseEntity.ok().body(Collections.singletonList(contatosPaciente));

        } catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @DeleteMapping("/deletar-contato/{idMedico}/{idContato}")
    public ResponseEntity<Object> deletarContatoMedico(@PathVariable("idMedico") Long idMedico, @PathVariable("idContato") Long idContato){
        try {

            medicoService.deletarContatoMedico(idMedico, idContato);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PostMapping("/adicionar-contato/{idMedico}")
    public ResponseEntity<Object> adicionarContatoPaciente(@RequestBody List<ContatoDto> contatoDto, @PathVariable("idMedico") Long idMedico){
        try {

            medicoService.adicionarContatoIdMedico(contatoDto, idMedico);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
    }

    @PutMapping("/alterar-contato/{id}")
    public ResponseEntity<Object> updateContatoMedico(@RequestBody ContatoDto contatoDto, @PathVariable("id") Long idMedico){
        try {

            medicoService.alterarContatoIdMedico(contatoDto, idMedico);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

}
