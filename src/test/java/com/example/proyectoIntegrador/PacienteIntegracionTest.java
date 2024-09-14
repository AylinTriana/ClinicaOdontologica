package com.example.proyectoIntegrador;

import com.example.proyectoIntegrador.entity.Domicilio;
import com.example.proyectoIntegrador.entity.Paciente;
import com.example.proyectoIntegrador.service.PacienteService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // No necesito login en esta clase
public class PacienteIntegracionTest {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MockMvc mockMvc;

    public void cargarPacientes() {
        Paciente paciente1= pacienteService.guardarPaciente(new Paciente("Matias","Santos","111111", LocalDate.of(2024,9,12),new Domicilio("Calle 1",122,"Uruguay","Montevideo"),"matiassantos@digitalhouse.com"));
        Paciente paciente2= pacienteService.guardarPaciente(new Paciente("Helen","Vasquez","1112221", LocalDate.of(2024,9,12),new Domicilio("Calle 2",122,"Lima","Peru"),"helenvasquez@digitalhouse.com"));
    }

    @Test
    public void listarTodosLosOdontologos() throws Exception {
        cargarPacientes();
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders
                        .get("/pacientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();


        assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void registrarNuevoPaciente() throws Exception {

        String pacienteJson = "{\"nombre\": \"Matias\", \"apellido\": \"Santos\", \"cedula\": \"111111\", \"fechaIngreso\": \"2024-09-12\", \"domicilio\": {\"calle\": \"Calle 1\", \"numero\": 122, \"localidad\": \"Uruguay\", \"provincia\": \"Montevideo\"}, \"email\": \"matiassantos@digitalhouse.com\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Matias"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellido").value("Santos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cedula").value("111111"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaIngreso").value("2024-09-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.domicilio.calle").value("Calle 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.domicilio.numero").value(122))
                .andExpect(MockMvcResultMatchers.jsonPath("$.domicilio.localidad").value("Uruguay"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.domicilio.provincia").value("Montevideo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("matiassantos@digitalhouse.com"));

    }

    @Test
    public void buscarPacientePorId() throws Exception {
        cargarPacientes();
        String id = "2";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pacientes/buscar/id/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Matias"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellido").value("Santos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cedula").value("111111"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaIngreso").value("2024-09-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.domicilio.calle").value("Calle 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.domicilio.numero").value(122))
                .andExpect(MockMvcResultMatchers.jsonPath("$.domicilio.localidad").value("Uruguay"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.domicilio.provincia").value("Montevideo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("matiassantos@digitalhouse.com"));
    }

    @Test
    public void actualizarPaciente() throws Exception {

        Paciente paciente = pacienteService.guardarPaciente(new Paciente("Helen","Vasquez","1112221", LocalDate.of(2024,9,12),new Domicilio("Calle 2",122,"Lima","Peru"),"helenvasquez@digitalhouse.com"));
        Long id = paciente.getId();
        String pacienteJson = "{\"id\": "+ id +",\"nombre\": \"Jose\", \"apellido\": \"Santos\", \"cedula\": \"111111\", \"fechaIngreso\": \"2024-09-12\", \"domicilio\": {\"id\": 1,\"calle\": \"Calle 1\", \"numero\": 122, \"localidad\": \"Uruguay\", \"provincia\": \"Montevideo\"}, \"email\": \"matiassantos@digitalhouse.com\"}";
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("paciente actualizado con exito"));

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders
                        .get("/pacientes/buscar/id/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseContent = resultado.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Jose"));
    }

    @Test
    public void eliminarOdontologo() throws Exception {
        Paciente paciente = pacienteService.guardarPaciente((new Paciente("Matias","Santos","111111", LocalDate.of(2024,9,12),new Domicilio("Calle 1",122,"Uruguay","Montevideo"),"matiassantos@digitalhouse.com")));
        Long id = paciente.getId();

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/pacientes/eliminar/" + id))
                .andExpect(status().isNoContent());

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders
                        .get("/pacientes/buscar/id/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(404, resultado.getResponse().getStatus());
    }

}
