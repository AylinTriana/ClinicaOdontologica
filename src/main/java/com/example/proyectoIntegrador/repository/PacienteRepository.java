package com.example.proyectoIntegrador.repository;

import com.example.proyectoIntegrador.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("SELECT pa FROM Paciente pa where pa.nombre=?1")
    Optional<Paciente> buscarPacientePorNombre(String nombre);
}
