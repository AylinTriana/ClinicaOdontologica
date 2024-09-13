package com.example.proyectoIntegrador.service;


import com.example.proyectoIntegrador.entity.Odontologo;
import com.example.proyectoIntegrador.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {

    @Autowired
    private OdontologoRepository odontologoRepository;

    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    public List<Odontologo> listarOdontologos() {
        return odontologoRepository.findAll();
    }

    public Optional<Odontologo> buscarPorID(Long id) {
        return odontologoRepository.findById(id);
    }

    public void actualizarOdontologo(Odontologo odontologo) {
        odontologoRepository.save(odontologo);
    }

    public void eliminarPorID(Long id) {
        odontologoRepository.deleteById(id);
    }

    public Optional<Odontologo> buscarPorNombre(String nombre){
        return odontologoRepository.buscarOdontologoPorNombre(nombre);
    }

}
