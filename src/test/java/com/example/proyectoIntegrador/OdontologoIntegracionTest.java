package com.example.proyectoIntegrador;

import com.example.proyectoIntegrador.entity.Odontologo;
import com.example.proyectoIntegrador.service.OdontologoService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class OdontologoIntegracionTest {

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private MockMvc mockMvc;

    public void cargarOdontologos() {
        odontologoService.guardarOdontologo(new Odontologo("Daniel", "Ruiz", "M1958"));
        odontologoService.guardarOdontologo(new Odontologo("Mariana", "Gómez", "M2000"));
    }

    @Test
    public void listarTodosLosOdontologos() throws Exception {

        cargarOdontologos();


        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders
                        .get("/odontologos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();


        assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void registrarNuevoOdontologo() throws Exception {
        String odontologoJson = "{ \"nombre\": \"Juan\", \"apellido\": \"Perez\", \"matricula\": \"M3000\" }";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(odontologoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Perez"))
                .andExpect(jsonPath("$.matricula").value("M3000"));
    }

    @Test
    public void buscarOdontologoPorId() throws Exception {
        cargarOdontologos();


        List<Odontologo> odontologos = odontologoService.listarOdontologos();
        odontologos.forEach(o -> System.out.println("Odontólogo ID: " + o.getId()));

        Long id = odontologos.get(0).getId();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/odontologos/buscar/id/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Mariana"))  // Ajusta según los datos cargados
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellido").value("Gómez"));  // Ajusta según los datos cargados
    }

    @Test
    public void actualizarOdontologo() throws Exception {
        cargarOdontologos();

        String odontologoActualizadoJson = "{ \"id\": 1, \"nombre\": \"Daniel\", \"apellido\": \"Lopez\", \"matricula\": \"M1958\" }";

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(odontologoActualizadoJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Odontologo actualizado"));
    }

    @Test
    public void eliminarOdontologo() throws Exception {
        cargarOdontologos();

        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/odontologos/eliminar/" + id))
                .andExpect(status().isNoContent());


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/odontologos/buscar/id/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}