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
    private OdontologoRepository odontologRepository;

    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologRepository.save(odontologo);
    }

    public List<Odontologo> listarOdontologos() {
        return odontologRepository.findAll();
    }

    public Optional<Odontologo> buscarPorID(Long id) {
        return odontologRepository.findById(id);
    }

    public void actualizarOdontologo(Odontologo odontologo) {
        odontologRepository.save(odontologo);
    }

    public void eliminarPorID(Long id) {
        odontologRepository.deleteById(id);
    }

    public Optional<Odontologo> buscarPorNombre(String nombre){
        return odontologRepository.buscarOdontologoPorNombre(nombre);
    }

}
