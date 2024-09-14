package com.example.proyectoIntegrador;

import com.example.proyectoIntegrador.entity.Domicilio;
import com.example.proyectoIntegrador.entity.Odontologo;
import com.example.proyectoIntegrador.entity.Paciente;
import com.example.proyectoIntegrador.entity.Turno;
import com.example.proyectoIntegrador.service.OdontologoService;
import com.example.proyectoIntegrador.service.PacienteService;
import com.example.proyectoIntegrador.service.TurnoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

    @SpringBootTest
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @AutoConfigureMockMvc(addFilters = false)
    public class TurnoIntegracionTest {
        @Autowired
        private TurnoService turnoService;
        @Autowired
        private PacienteService pacienteService;
        @Autowired
        private OdontologoService odontologoService;
        @Autowired
        private MockMvc mockMvc;

        public void cargarTurnos(){
            Paciente paciente1= pacienteService.guardarPaciente(new Paciente("Matias","Santos","111111", LocalDate.of(2024,9,12),new Domicilio("Calle 1",122,"Uruguay","Montevideo"),"matiassantos@digitalhouse.com"));
            Paciente paciente2= pacienteService.guardarPaciente(new Paciente("Helen","Vasquez","1112221", LocalDate.of(2024,9,12),new Domicilio("Calle 2",122,"Lima","Peru"),"helenvasquez@digitalhouse.com"));
            Odontologo odontologo1= odontologoService.guardarOdontologo(new Odontologo("Juan","Maldonado","MP10"));
            Odontologo odontologo2= odontologoService.guardarOdontologo(new Odontologo("Daniela","Paz","MP20"));
            Turno turno1= turnoService.guardarTurno(new Turno(paciente1,odontologo1,LocalDate.of(2024,11,12)));
            Turno turno2= turnoService.guardarTurno(new Turno(paciente2,odontologo2,LocalDate.of(2024,12,20)));
        }


        @Test
        @Order(1)
        public void listarTodosLosTurnos() throws Exception{
            cargarTurnos();
            MvcResult resultado= mockMvc.perform(MockMvcRequestBuilders.get("/turno").accept(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            assertFalse(resultado.getResponse().getContentAsString().isEmpty());

        }


        @Test
        @Order(2)
        public void registrarNuevoTurno() throws Exception {
        String turnoJson = "{"

                + "\"paciente\": {\"id\": 1},"
                + "\"odontologo\": {\"id\": 2},"
                + "\"fecha\": \"2024-12-12\""
                + "}";

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/turno")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(turnoJson))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.paciente.id").value(1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.odontologo.id").value(2))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.fecha").value("2024-12-12"));


        }



    @Test
    @Order(3)
    public void actualizarTurno() throws Exception {
        String turnoJson = "{"
                + "\"id\": 1,"
                + "\"paciente\": {\"id\": 1},"
                + "\"odontologo\": {\"id\": 2},"
                + "\"fecha\": \"2024-12-12\""
                + "}";

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/turno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(turnoJson))
                .andExpect(status().isOk());

    }

    @Test
    @Order(4)
    public void eliminarTurno() throws Exception {
        String id = "2";

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/turno/eliminar/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders
                        .get("/turno/buscar/id/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(404, resultado.getResponse().getStatus());
    }


    @Test
    @Order(5)
    public void buscarTurnoPorID() throws Exception {
        String id = "1";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/turno/buscar/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paciente.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.odontologo.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fecha").value("2024-12-12"));
    }



}












