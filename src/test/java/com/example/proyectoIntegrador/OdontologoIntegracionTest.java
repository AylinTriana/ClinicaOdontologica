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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // No necesito login en esta clase
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Juan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellido").value("Perez"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matricula").value("M3000"));
    }

    @Test
    public void buscarOdontologoPorId() throws Exception {
        cargarOdontologos();  // Cargar los odontólogos en la base de datos

        // Imprime todos los odontólogos para verificar que se han guardado correctamente
        List<Odontologo> odontologos = odontologoService.listarOdontologos();
        odontologos.forEach(o -> System.out.println("Odontólogo ID: " + o.getId()));

        Long id = odontologos.get(0).getId();  // Usa un ID válido

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/odontologos/buscar/id/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Daniel"))  // Ajusta según los datos cargados
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellido").value("Ruiz"));  // Ajusta según los datos cargados
    }
    @Test
    public void actualizarOdontologo() throws Exception {
        Odontologo odontologo = odontologoService.guardarOdontologo(new Odontologo("Laura", "Pérez", "M6000"));
        Long id = odontologo.getId();

        String odontologoJson = "{ \"id\": " + id + ", \"nombre\": \"Laura\", \"apellido\": \"Pérez\", \"matricula\": \"M6001\" }";

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(odontologoJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("odontologo actualizado con exito"));

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders
                        .get("/odontologos/buscar/id/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseContent = resultado.getResponse().getContentAsString();
        assertTrue(responseContent.contains("M6001"));
    }

    @Test
    public void eliminarOdontologo() throws Exception {
        Odontologo odontologo = odontologoService.guardarOdontologo(new Odontologo("Ana", "García", "M7000"));
        Long id = odontologo.getId();

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/odontologos/eliminar/" + id))
                .andExpect(status().isNoContent());

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders
                        .get("/odontologos/buscar/id/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(404, resultado.getResponse().getStatus());
    }

}
