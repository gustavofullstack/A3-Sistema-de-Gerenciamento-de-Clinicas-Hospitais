package com.example.clinica_medica.controller;

import com.example.clinica_medica.domain.dto.MedicoDto;
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

    @GetMapping("/todos")
    public ResponseEntity<List<Object>> buscarTodosMedicos(){
        try{

            List<MedicoDto> medicos = medicoService.buscarTodosMedicos();

            if (medicos.isEmpty()){
                throw new Exception("Não existe medicos!");
            }

            return ResponseEntity.ok(Collections.singletonList(medicos));

        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> consultarDadosBasicosPacientePeloId(@PathVariable Long id) {
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

    @PostMapping("/salvar")
    public ResponseEntity<Object> salvarMedico(@RequestBody MedicoDto medicoDto){
        try {

            medicoService.salvarMedico(medicoDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
    }

    @PutMapping("/{idMedico}")
    public ResponseEntity<Object> updateMedico(@RequestBody MedicoDto medicoDto, @PathVariable("idMedico") Long id){
        try {

            medicoDto.setId(id);
            medicoService.alterarMedico(medicoDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idMedico}")
    public ResponseEntity<Object> deletarMedico(@PathVariable("idMedico") Long id){
        try {

            medicoService.deletarMedico(id);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

}
