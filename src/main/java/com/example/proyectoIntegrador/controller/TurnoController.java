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
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Turno> registrarTurno(@RequestBody Turno turno) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorID(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorID(turno.getOdontologo().getId());
        if (pacienteBuscado.isPresent() && odontologoBuscado.isPresent()) {
            turno.setPaciente(pacienteBuscado.get());
            turno.setOdontologo(odontologoBuscado.get());
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listarTodos(){
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    @PutMapping
    public void actualizarTurno(Turno turno) {
        turnoService.guardarTurno(turno);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarTurno(Long id) {
        turnoService.eliminarTurno(id);
    }

    @GetMapping("/buscar/{id}")
    public Optional<Turno> buscarPorId(Long id) {
        return turnoService.buscarPorId(id);
    }
}
