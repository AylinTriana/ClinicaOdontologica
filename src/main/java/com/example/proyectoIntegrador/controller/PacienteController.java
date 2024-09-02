package com.example.proyectoIntegrador.controller;



import com.example.proyectoIntegrador.entity.Paciente;
import com.example.proyectoIntegrador.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

  @PostMapping
  public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente){
      return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
  }

  @PutMapping
  public ResponseEntity<Void> actualizarPaciente(@RequestBody Paciente paciente) {
      pacienteService.actualizarPaciente(paciente);
      return ResponseEntity.noContent().build();
  }

  @GetMapping("buscar/id/{id}")
  public ResponseEntity<Optional<Paciente>> buscarPacienteID(@PathVariable Long id) {
      return ResponseEntity.ok(pacienteService.buscarPorID(id));
  }

  @GetMapping("buscar/nombre/{nombre}")
  public ResponseEntity<Optional<Paciente>> buscarPacienteNombre(@PathVariable String nombre) {
      return ResponseEntity.ok(pacienteService.buscarPacientePorNombre(nombre));
  }

    @DeleteMapping("/eliminar/{id}")
  public ResponseEntity<Void> eliminarPacienteID(@PathVariable Long id) {
        pacienteService.eliminarPacienteID(id);
        return ResponseEntity.noContent().build();
  }

  @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
      return ResponseEntity.ok(pacienteService.listarTodos());
  }

}
