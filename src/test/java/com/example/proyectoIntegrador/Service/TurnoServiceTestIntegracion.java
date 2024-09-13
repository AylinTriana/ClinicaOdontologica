package com.example.proyectoIntegrador.Service;

import com.example.proyectoIntegrador.entity.Domicilio;
import com.example.proyectoIntegrador.entity.Odontologo;
import com.example.proyectoIntegrador.entity.Paciente;
import com.example.proyectoIntegrador.entity.Turno;
import com.example.proyectoIntegrador.service.OdontologoService;
import com.example.proyectoIntegrador.service.PacienteService;
import com.example.proyectoIntegrador.service.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TurnoServiceTestIntegracion {

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private MockMvc mockMvc;


    public void cargarTurnos(){
        Paciente paciente1= pacienteService.guardarPaciente(new Paciente("Michael","Sepulveda","145237", LocalDate.of(2024,9,10),new Domicilio("Calle 23",142,"Uruguay","Montevideo"),"michael@michael.com"));
        Paciente paciente2= pacienteService.guardarPaciente(new Paciente("Natalia","Vasquez","456932", LocalDate.of(2024,9,9),new Domicilio("Calle 12",232,"Lima","Peru"),"nata@nta.com"));
        Odontologo odontologo1= odontologoService.guardarOdontologo(new Odontologo("Julian","Maldonado","MP10"));
        Odontologo odontologo2= odontologoService.guardarOdontologo(new Odontologo("Daniela","Paz","MP20"));
        Turno turnoDTO1= turnoService.guardarTurno(new Turno(paciente1,odontologo1,LocalDate.of(2024,11,12)));
        Turno turnoDTO2= turnoService.guardarTurno(new Turno(paciente2,odontologo2,LocalDate.of(2024,12,20)));
    }


    @Test
    public void listarTodosLosTurnos() throws Exception{

        cargarTurnos();
        MvcResult resultado= mockMvc.perform(MockMvcRequestBuilders
                        .get("/turnos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertFalse(resultado.getResponse().getContentAsString().isEmpty());

    }
}
