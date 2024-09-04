package com.example.proyectoIntegrador.repository;

import com.example.proyectoIntegrador.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OdontologRepository extends JpaRepository<Odontologo, Long> {

    @Query("SELECT odt FROM Odontologo odt WHERE UPPER (odt.nombre) = UPPER (?1)")
    Optional<Odontologo> buscarOdontologoPorNombre(String nombre);



}
