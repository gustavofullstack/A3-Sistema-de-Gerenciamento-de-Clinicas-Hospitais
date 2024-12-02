package com.example.clinica_medica.controller;

import com.example.clinica_medica.domain.dto.PrescricaoDto;
import com.example.clinica_medica.domain.service.PrescricaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/prescricoes")
public class PrescricaoController {
    
    @Autowired
    private PrescricaoService prescricaoService;

    @GetMapping("/buscar-todos")
    public ResponseEntity<List<Object>> buscarTodasPrescricoes(){
        try {

            List<PrescricaoDto> prescricao = prescricaoService.buscarTodasPrescricao();

            if (prescricao.isEmpty()){
                return ResponseEntity.ok(Collections.singletonList("Não há nenhuma prescricao!"));
            }

            return ResponseEntity.ok(Collections.singletonList(prescricao));

        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @GetMapping("/buscar-prescricao/{idPrescricao}")
    public ResponseEntity<Object> prescricaorPeloId(@PathVariable("idPrescricao") Long idPrescricao) {
        try {

            PrescricaoDto prescricaoDto = prescricaoService.buscarPrescricaoId(idPrescricao);

            if(Optional.ofNullable(prescricaoDto).isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok().body(prescricaoDto);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/adicionar-prescricao")
    public ResponseEntity<Object> adicionarPrescricao(@Valid @RequestBody PrescricaoDto prescricaoDto){
        try {

            prescricaoService.adicionarPrescricao(prescricaoDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PutMapping("/editar-prescricao")
    public ResponseEntity<Object> alterarPrescricao(@Valid @RequestBody PrescricaoDto prescricaoDto){
        try {

            prescricaoService.alterarPrescricao(prescricaoDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping("/excluir-prescricao/{idPrescricao}")
    public ResponseEntity<Object> deletarPrescricao(@PathVariable("idPrescricao") Long idPrescricao){
        try {

            prescricaoService.deletarPrescricao(idPrescricao);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
    
}
