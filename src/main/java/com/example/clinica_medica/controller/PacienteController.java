package com.example.clinica_medica.controller;

import com.example.clinica_medica.domain.dto.ContatoDto;
import com.example.clinica_medica.domain.dto.EnderecoDto;
import com.example.clinica_medica.domain.dto.PacienteDto;
import com.example.clinica_medica.domain.dto.PacienteSimplificadoDto;
import com.example.clinica_medica.domain.exception.BusinessException;
import com.example.clinica_medica.domain.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/buscar-todos")
    public ResponseEntity<List<Object>> buscarTodosPacientes(){
        try{

            List<PacienteSimplificadoDto> paciente = pacienteService.buscarTodosPacientes();

            if (paciente.isEmpty()){
                throw new Exception("Não existe paciente!");
            }

            return ResponseEntity.ok(Collections.singletonList(paciente));

        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @GetMapping("/buscar-id/{idPaciente}")
    public ResponseEntity<Object> consultarDadosPacientesPeloId(@PathVariable("idPaciente") Long idPaciente) {
        try {

            PacienteDto pacienteDto = pacienteService.buscarDadosPacientePeloId(idPaciente);

            if(Optional.ofNullable(pacienteDto).isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok().body(pacienteDto);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/cadastro-completo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> cadastroCompleto(@Valid @RequestBody PacienteDto pacienteDto){
        try {

            pacienteService.cadastroCompleto(pacienteDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
    }

    @PutMapping("/alterar-id/{idPaciente}")
    public ResponseEntity<Object> updatePaciente(@Valid @RequestBody PacienteSimplificadoDto pacienteDto, @PathVariable("idPaciente") Long id){
        try {

            pacienteDto.setId(id);
            pacienteService.alterarPaciente(pacienteDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar-id/{idPaciente}")
    public ResponseEntity<Object> deletarPaciente(@PathVariable("idPaciente") Long id){
        try {

            pacienteService.deletarPaciente(id);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/buscar-enderecos/{idPaciente}")
    public ResponseEntity<List<Object>> consultarEnderecosPeloIdPaciente(@PathVariable("idPaciente") Long idPaciente){
        try {

            List<EnderecoDto> enderecosPaciente = pacienteService.buscarEnderecosPaciente(idPaciente);

            if(enderecosPaciente.isEmpty()){
                throw new BusinessException("Nenhum endereço encontrado para o paciente");
            }
            return ResponseEntity.ok().body(Collections.singletonList(enderecosPaciente));

        } catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @PutMapping("/alterar-endereco/{id}")
    public ResponseEntity<Object> updateEnderecoPaciente(@Valid @RequestBody EnderecoDto enderecoDto, @PathVariable("id") Long idPaciente){
        try {

                pacienteService.alterarEnderecoPaciente(enderecoDto, idPaciente);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar-endereco/{idPaciente}/{idEndereco}")
    public ResponseEntity<Object> deletarMedico(@PathVariable("idPaciente") Long idPaciente, @PathVariable("idEndereco") Long idEndereco){
        try {

            pacienteService.deletarEnderecoPaciente(idPaciente, idEndereco);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PostMapping("/adicionar-endereco/{idPaciente}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> adicionarEnderecoPaciente(@Valid @RequestBody List<EnderecoDto> enderecosDto, @PathVariable("idPaciente") Long idPaciente){
        try {

            pacienteService.adicionarEnderecoIdPaciente(enderecosDto, idPaciente);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
    }

    @GetMapping("/buscar-contatos/{idPaciente}")
    public ResponseEntity<List<Object>> consultarContatosPeloIdPaciente(@PathVariable("idPaciente") Long idPaciente){
        try {

            List<ContatoDto> contatosPaciente = pacienteService.buscarContatosPeloIdPaciente(idPaciente);

            if(contatosPaciente.isEmpty()){
                throw new BusinessException("Nenhum contato encontrado para o paciente");
            }
            return ResponseEntity.ok().body(Collections.singletonList(contatosPaciente));

        } catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @DeleteMapping("/deletar-contato/{idPaciente}/{idContato}")
    public ResponseEntity<Object> deletarContatoMedico(@PathVariable("idPaciente") Long idPaciente, @PathVariable("idContato") Long idContato){
        try {

            pacienteService.deletarContatoPaciente(idPaciente, idContato);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PostMapping("/adicionar-contato/{idPaciente}")
    public ResponseEntity<Object> adicionarContatoPaciente(@Valid @RequestBody List<ContatoDto> contatoDto, @PathVariable("idPaciente") Long idPaciente){
        try {

            pacienteService.adicionarContatoIdPaciente(contatoDto, idPaciente);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
    }

    @PutMapping("/alterar-contato/{id}")
    public ResponseEntity<Object> updateContatoPaciente(@Valid @RequestBody ContatoDto contatoDto, @PathVariable("id") Long IdPaciente){
        try {

            pacienteService.alterarContatoIdPaciente(contatoDto, IdPaciente);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

}
