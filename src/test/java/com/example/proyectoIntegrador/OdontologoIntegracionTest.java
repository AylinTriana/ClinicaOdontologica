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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

}
