package com.example.clinica_medica.controller;

import com.example.clinica_medica.domain.dto.PacienteDto;
import com.example.clinica_medica.domain.service.PacienteService;
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

    @GetMapping("/todos")
    public ResponseEntity<List<Object>> buscarTodosPacientes(){
        try{

            List<PacienteDto> paciente = pacienteService.buscarTodosPacientes();

            if (paciente.isEmpty()){
                throw new Exception("Não existe paciente!");
            }

            return ResponseEntity.ok(Collections.singletonList(paciente));

        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> consultarTodosDadosPacientesPeloId(@PathVariable Long id) {
        try {

            PacienteDto pacienteDto = pacienteService.consultarDadosPacientePeloId(id);

            if(Optional.ofNullable(pacienteDto).isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok().body(pacienteDto);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/cadastroCompleto")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> cadastroCompleto(@RequestBody PacienteDto pacienteDto){
        try {

            pacienteService.cadastroCompleto(pacienteDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
    }

    @PutMapping("/{idPaciente}")
    public ResponseEntity<Object> updatePaciente(@RequestBody PacienteDto pacienteDto, @PathVariable("idPaciente") Long id){
        try {

            pacienteDto.setId(id);
            pacienteService.alterarPaciente(pacienteDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idPaciente}")
    public ResponseEntity<Object> deletarPaciente(@PathVariable("idPaciente") Long id){
        try {

            pacienteService.deletarPaciente(id);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

}
