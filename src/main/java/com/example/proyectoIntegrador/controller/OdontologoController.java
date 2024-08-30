package com.example.proyectoIntegrador.controller;

import com.example.proyectoIntegrador.entity.Odontologo;
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
            return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
        }

        @PutMapping
        public ResponseEntity<Void> actualizarOdontologo(@RequestBody Odontologo odontologo) {
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.noContent().build(); // Devuelve 204 No Content si la actualización fue exitosa
    }

        @GetMapping
        public ResponseEntity<List<Odontologo>> listarTodos() {
            return ResponseEntity.ok(odontologoService.listarOdontologos());
        }

        @GetMapping("/buscar/id/{id}")
        public ResponseEntity<Optional<Odontologo>> buscarPorId(@PathVariable Long id) {
            return ResponseEntity.ok(odontologoService.buscarPorID(id));
        }

        @GetMapping("/buscar/nombre/{nombre}")
        public ResponseEntity<Optional<Odontologo>> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(odontologoService.buscarPorNombre(nombre));
    }

        @DeleteMapping("/eliminar/{id}")
        public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
            odontologoService.eliminarPorID(id);
            return ResponseEntity.noContent().build();
        }


    }

