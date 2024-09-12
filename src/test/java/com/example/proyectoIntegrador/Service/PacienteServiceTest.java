package com.example.proyectoIntegrador.Service;

import com.example.proyectoIntegrador.entity.Domicilio;
import com.example.proyectoIntegrador.entity.Paciente;
import com.example.proyectoIntegrador.service.PacienteService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    public void guardarPaciente(){
        Paciente paciente = new Paciente("Santiago","Cortez","152036", LocalDate.of(2024,9,10),new Domicilio("Calle 11",15,"Kennedy","Kennedy"),"santi@santi.com");
        Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);
        assertEquals(1L,pacienteGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarPaciente(){
        Long id = 1L;
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorID(id);
        assertNotNull(pacienteBuscado.get());
    }

    @Test
    @Order(3)
    public void actualizarPaciente(){
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(1L);
        if(pacienteBuscado.isPresent()){
            pacienteBuscado.get().setApellido("Mendez");
        }
        pacienteService.actualizarPaciente(pacienteBuscado.get());
        assertEquals("Mendez",pacienteBuscado.get().getApellido());
    }

    @Test
    @Order(4)
    public void listarTodos(){
        List<Paciente> pacientes= pacienteService.listarTodos();
        assertEquals(1,pacientes.size());
    }

    @Test
    @Order(5)
    public void eliminaPaciente(){
        pacienteService.eliminarPacienteID(1L);
        Optional<Paciente> pacienteEliminado= pacienteService.buscarPorID(1L);
        assertFalse(pacienteEliminado.isPresent());
    }
}
