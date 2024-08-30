package com.example.proyectoIntegrador.controller;


import com.example.proyectoIntegrador.entity.Odontologo;
import com.example.proyectoIntegrador.entity.Paciente;
import com.example.proyectoIntegrador.entity.Turno;
import com.example.proyectoIntegrador.service.OdontologoService;
import com.example.proyectoIntegrador.service.PacienteService;
import com.example.proyectoIntegrador.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/turno")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Turno> registrarTurno(@RequestBody Turno turno) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorID(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorID(turno.getOdontologo().getId());
        if (pacienteBuscado.isPresent() && odontologoBuscado.isPresent()) {
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listarTodos(){
        return ResponseEntity.ok(turnoService.listarTurnos());
    }
}
