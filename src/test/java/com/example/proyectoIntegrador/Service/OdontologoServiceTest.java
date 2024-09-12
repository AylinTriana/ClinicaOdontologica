package com.example.proyectoIntegrador.Service;

import com.example.proyectoIntegrador.entity.Odontologo;
import com.example.proyectoIntegrador.service.OdontologoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarOdontologo() {
        Odontologo odontologo = new Odontologo("Daniel", "Ruiz", "M1958");
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        assertEquals(1L, odontologoGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarOdontolgoPorId() {
        Long id = 1L;
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorID(id);
        assertNotNull(odontologoBuscado.get());
    }

    @Test
    @Order(3)
    public void actualizarOdontologo(){
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(1L);
        if(odontologoBuscado.isPresent()){
            odontologoBuscado.get().setApellido("Sepulveda");
        }
        odontologoService.actualizarOdontologo(odontologoBuscado.get());
        assertEquals("Sepulveda",odontologoBuscado.get().getApellido());
    }

    @Test
    @Order(4)
    public void listarTodos(){
        List<Odontologo> odontologos= odontologoService.listarOdontologos();
        assertEquals(1,odontologos.size());
    }

    @Test
    @Order(5)
    public void eliminaPaciente(){
        odontologoService.eliminarPorID(1L);
        Optional<Odontologo> odontologoEliminado = odontologoService.buscarPorID(1L);
        assertFalse(odontologoEliminado.isPresent());
    }
}
