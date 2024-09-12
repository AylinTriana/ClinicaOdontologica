package com.example.proyectoIntegrador.controller;

import com.example.proyectoIntegrador.entity.Paciente;
import com.example.proyectoIntegrador.exception.BadRequestException;
import com.example.proyectoIntegrador.exception.ResourceNotFoundException;
import com.example.proyectoIntegrador.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
      return ResponseEntity.status(200).body(pacienteService.guardarPaciente(paciente));
  }

  @PutMapping
  public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente) throws ResourceNotFoundException {
      Optional<Paciente> pacienteBuscado = pacienteService.buscarPorID(paciente.getId());
      if (pacienteBuscado.isPresent()) {
          pacienteService.actualizarPaciente(paciente);
          return ResponseEntity.status(200).body("paciente actualizado con exito");
      } else {
          throw new ResourceNotFoundException("Paciente no encontrado");
      }
  }

  @GetMapping("buscar/id/{id}")
  public ResponseEntity<Optional<Paciente>> buscarPacienteID(@PathVariable Long id) throws ResourceNotFoundException {
      Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(id);
      if(pacienteBuscado.isPresent()){
          return ResponseEntity.status(200).body(pacienteBuscado);
      }

      throw new ResourceNotFoundException("Paciente no encontrado");
  }
  @GetMapping("buscar/nombre/{nombre}")
  public ResponseEntity<Optional<Paciente>> buscarPacienteNombre(@PathVariable String nombre) throws ResourceNotFoundException {
      Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorNombre(nombre);
      if (pacienteBuscado.isPresent()) {
          return ResponseEntity.status(200).body(Optional.of(pacienteBuscado.get()));
      } else {
          throw new ResourceNotFoundException("Paciente no encontrado");
      }
  }
    @DeleteMapping("/eliminar/{id}")
  public ResponseEntity<String> eliminarPacienteID(@PathVariable("id") Long id) throws BadRequestException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorID(id);
        if(pacienteBuscado.isPresent()){
            pacienteService.eliminarPacienteID(id);
            return ResponseEntity.status(200).body("paciente eliminado con exito");
        }else{
            throw new BadRequestException("Paciente no encontrado");
        }
    }

  @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
      return ResponseEntity.status(200).body(pacienteService.listarTodos());
  }

}
