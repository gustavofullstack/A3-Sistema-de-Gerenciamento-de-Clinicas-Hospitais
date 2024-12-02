package com.example.clinica_medica.controller;

import com.example.clinica_medica.domain.dto.ConsultaDto;
import com.example.clinica_medica.domain.dto.ConsultaSimplificadaDto;
import com.example.clinica_medica.domain.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping("/buscar-todos")
    public ResponseEntity<List<Object>> buscarTodasConsultas(){
        try {

            List<ConsultaSimplificadaDto> consultas = consultaService.buscarTodasConsultas();

            if (consultas.isEmpty()){
                return ResponseEntity.ok(Collections.singletonList("Não há nenhuma consulta marcada!"));
            }

            return ResponseEntity.ok(Collections.singletonList(consultas));

        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @GetMapping("/buscar-consulta/{idConsulta}")
    public ResponseEntity<Object> consultarDadosMedicoPeloId(@PathVariable("idConsulta") Long idConsulta) {
        try {

            ConsultaDto consultaDto = consultaService.buscarConsultaId(idConsulta);

            if(Optional.ofNullable(consultaDto).isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok().body(consultaDto);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/agendar-consulta")
    public ResponseEntity<Object> agendarConsultas(@Valid @RequestBody ConsultaSimplificadaDto consultaSimplificadaDto){
        try {

            consultaService.agendarConsulta(consultaSimplificadaDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PutMapping("/editar-consulta")
    public ResponseEntity<Object> alterarConsulta(@Valid @RequestBody ConsultaSimplificadaDto consultaSimplificadaDto){
        try {

            consultaService.alterarConsulta(consultaSimplificadaDto);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping("/excluir-consulta/{idConsulta}")
    public ResponseEntity<Object> deletarConsulta(@PathVariable("idConsulta") Long idConsulta){
        try {

            consultaService.deletarConsulta(idConsulta);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

}
