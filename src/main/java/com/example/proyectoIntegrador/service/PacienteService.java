package com.example.proyectoIntegrador.service;


import com.example.proyectoIntegrador.entity.Paciente;
import com.example.proyectoIntegrador.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPorID(Long id) {
        return pacienteRepository.findById(id);
    }

    public Optional<Paciente> buscarPacientePorNombre(String nombre) {
        return pacienteRepository.buscarPacientePorNombre(nombre);
    }

    public void eliminarPacienteID(Long id) {
        pacienteRepository.deleteById(id);
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public void actualizarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

}
