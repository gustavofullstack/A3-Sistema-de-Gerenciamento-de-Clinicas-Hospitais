package com.example.clinica_medica.controller;

import com.example.clinica_medica.domain.dto.ExameDto;
import com.example.clinica_medica.domain.service.ExameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/exames")
public class ExameController {

    @Autowired
    private ExameService exameService;
    
    @GetMapping("/buscar-todos")
    public ResponseEntity<List<Object>> buscarTodasExame(){
        try {

            List<ExameDto> exame = exameService.buscarTodasExame();

            if (exame.isEmpty()){
                return ResponseEntity.ok(Collections.singletonList("Não há nenhuma exame!"));
            }

            return ResponseEntity.ok(Collections.singletonList(exame));

        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @GetMapping("/buscar-exame/{idExame}")
    public ResponseEntity<Object> exameDadosMedicoPeloId(@PathVariable("idExame") Long idExame) {
        try {

            ExameDto exameDto = exameService.buscarExameId(idExame);

            if(Optional.ofNullable(exameDto).isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok().body(exameDto);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/adicionar-exame")
    public ResponseEntity<Object> agendarExame(@Valid @RequestBody ExameDto exameDto){
        try {

            exameService.adicionarExame(exameDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PutMapping("/editar-exame")
    public ResponseEntity<Object> alterarExame(@Valid @RequestBody ExameDto exameDto){
        try {

            exameService.alterarExame(exameDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping("/excluir-exame/{idExame}")
    public ResponseEntity<Object> deletarExame(@PathVariable("idExame") Long idExame){
        try {

            exameService.deletarExame(idExame);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

}
