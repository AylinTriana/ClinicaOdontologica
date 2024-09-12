package com.example.proyectoIntegrador.Service;

import com.example.proyectoIntegrador.entity.Domicilio;
import com.example.proyectoIntegrador.entity.Odontologo;
import com.example.proyectoIntegrador.entity.Paciente;
import com.example.proyectoIntegrador.entity.Turno;
import com.example.proyectoIntegrador.repository.OdontologoRepository;
import com.example.proyectoIntegrador.repository.PacienteRepository;
import com.example.proyectoIntegrador.service.TurnoService;
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
public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private OdontologoRepository odontologoRepository;

    @Test
    @Order(1)
    public void guardarTurno(){
        Paciente paciente1 = new Paciente(1L,"Ana","Ferrer", "152377", LocalDate.of(2024,9,23), new Domicilio("calle 16", 45,"La Rioja","Argentina"), "ana@anita.com");
        pacienteRepository.save(paciente1);
        Odontologo odontologo1 = new Odontologo(1L,"Esteban","Mendez", "K19435");
        odontologoRepository.save(odontologo1);

        Turno turno = new Turno(paciente1,odontologo1, LocalDate.of(2024, 9, 20));
        Turno turnoGuardado = turnoService.guardarTurno(turno);
        assertNotNull(turnoGuardado.getId());

    }

    @Test
    @Order(2)
    public void buscarTurnoPorId(){
        Long id = 1L;
        Optional<Turno> turnoBuscado = turnoService.buscarPorId(id);
        assertNotNull(turnoBuscado.get());
    }

    @Test
    @Order(3)
    public void actualizarTurno() {
        Long id = 1L;

        Optional<Turno> turnoOptional = turnoService.buscarPorId(id);
        if (turnoOptional.isPresent()) {
            Turno turno = turnoOptional.get();

            // Actualizar la fecha del turno
            turno.setFecha(LocalDate.of(2024, 9, 15));

            // Guardar el turno actualizado
            turnoService.actualizarTurno(turno);

            // Verificar que la fecha del turno fue actualizada
            Optional<Turno> turnoActualizado = turnoService.buscarPorId(turno.getId());
            assertTrue(turnoActualizado.isPresent());
            assertEquals(LocalDate.of(2024, 9, 15), turnoActualizado.get().getFecha());
        } else {
            System.out.println("Turno no encontrado con el ID: " + id);
        }
    }

    @Test
    @Order(4)
    public void ListarTodos(){
        List<Turno> listaTurnos= turnoService.listarTurnos();
        assertEquals(1,listaTurnos.size());
    }

    @Test
    @Order(5)
    public void eliminarTurno(){
        turnoService.eliminarTurno(1L);
        Optional<Turno> turnoEliminado= turnoService.buscarPorId(1L);
        assertFalse(turnoEliminado.isPresent());
    }

}


