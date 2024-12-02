package com.example.clinica_medica.controller;

import com.example.clinica_medica.domain.dto.HistoricoMedicoDto;
import com.example.clinica_medica.domain.service.HistoricoMedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/historico-medico")
public class HistoricoMedicoController {

    @Autowired
    private HistoricoMedicoService historicoMedicoService;

    @GetMapping("/buscar-todos")
    public ResponseEntity<List<Object>> buscarTodoHistoricoMedico(){
        try {

            List<HistoricoMedicoDto> historicoMedico = historicoMedicoService.buscarTodoHistoricoMedico();

            if (historicoMedico.isEmpty()){
                return ResponseEntity.ok(Collections.singletonList("Não há nenhuma historicoMedico!"));
            }

            return ResponseEntity.ok(Collections.singletonList(historicoMedico));

        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @GetMapping("/buscar-historico-medico/{idHistoricoMedico}")
    public ResponseEntity<Object> historicoMedicoPeloId(@PathVariable("idHistoricoMedico") Long idHistoricoMedico) {
        try {

            HistoricoMedicoDto historicoMedicoDto = historicoMedicoService.buscarHistoricoMedicoId(idHistoricoMedico);

            if(Optional.ofNullable(historicoMedicoDto).isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok().body(historicoMedicoDto);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/adicionar-historico-medico")
    public ResponseEntity<Object> adicionarHistoricoMedico(@Valid @RequestBody HistoricoMedicoDto historicoMedicoDto){
        try {

            historicoMedicoService.adicionarHistoricoMedico(historicoMedicoDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PutMapping("/editar-historico-medico")
    public ResponseEntity<Object> alterarHistoricoMedico(@Valid @RequestBody HistoricoMedicoDto historicoMedicoDto){
        try {

            historicoMedicoService.alterarHistoricoMedico(historicoMedicoDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping("/excluir-historico-medico/{idHistoricoMedico}")
    public ResponseEntity<Object> deletarHistoricoMedico(@PathVariable("idHistoricoMedico") Long idHistoricoMedico){
        try {

            historicoMedicoService.deletarHistoricoMedico(idHistoricoMedico);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
    
}
