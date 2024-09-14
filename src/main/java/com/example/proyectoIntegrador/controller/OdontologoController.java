package com.example.proyectoIntegrador.controller;

import com.example.proyectoIntegrador.entity.Odontologo;
import com.example.proyectoIntegrador.exception.BadRequestException;
import com.example.proyectoIntegrador.exception.ResourceNotFoundException;
import com.example.proyectoIntegrador.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Odontologo> guardarOdontologo(@RequestBody Odontologo odontologo) {
        return ResponseEntity.status(200).body(odontologoService.guardarOdontologo(odontologo));
    }

    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorID(odontologo.getId());
        if (odontologoBuscado.isPresent()) {
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.status(200).body("odontologo actualizado con exito");
        } else {
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
    }

        @GetMapping
        public ResponseEntity<List<Odontologo>> listarTodos() {
            return ResponseEntity.status(200).body(odontologoService.listarOdontologos());
        }

        @GetMapping("/buscar/id/{id}")
        public ResponseEntity<Optional<Odontologo>> buscarPorID(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(id);
        if(odontologoBuscado.isPresent()){
            return ResponseEntity.status(200).body(odontologoBuscado);
        }

        throw new ResourceNotFoundException("Odontologo no encontrado");
    }

        @GetMapping("buscar/nombre/{nombre}")
        public ResponseEntity<Optional<Odontologo>> buscarOdntologoNombre(@PathVariable String nombre) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorNombre(nombre);
        if (odontologoBuscado.isPresent()) {
            return ResponseEntity.status(200).body(Optional.of(odontologoBuscado.get()));
        } else {
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
    }

        @DeleteMapping("/eliminar/{id}")
        public ResponseEntity<String> eliminarOdontologoID(@PathVariable("id") Long id) throws BadRequestException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorID(id);
        if(odontologoBuscado.isPresent()){
            odontologoService.eliminarPorID(id);
            return ResponseEntity.status(204).body("odontologo eliminado con exito");
        }else{
            throw new BadRequestException("Odontologo no encontrado");
        }
    }


}

