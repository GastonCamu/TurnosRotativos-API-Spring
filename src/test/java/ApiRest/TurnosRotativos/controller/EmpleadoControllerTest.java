package ApiRest.TurnosRotativos.controller;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.service.EmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(EmpleadoController.class)
public class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpleadoService empleadoService;

    @InjectMocks
    private EmpleadoController empleadoController;

    private ObjectMapper objectMapper;
    private EmpleadoDTO empleadoDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Registra el módulo para LocalDate

        empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setNombre("Gaston");
        empleadoDTO.setApellido("Jouse");
        empleadoDTO.setNroDocumento(32444321);
        empleadoDTO.setEmail("gastonjouse@gmail.com");
        empleadoDTO.setFechaNacimiento(LocalDate.of(2002, 9, 1));
        empleadoDTO.setFechaIngreso(LocalDate.of(2023, 9, 1));
    }

    @Test
    void getEmpleado_deberiaRetornarEmpleado() throws Exception {
        when(empleadoService.getEmpleado(anyInt())).thenReturn(empleadoDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/empleado/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Gaston"));
    }

    @Test
    void getEmpleados_deberiaRetornarListaDeEmpleados() throws Exception {
        when(empleadoService.getEmpleados()).thenReturn(Collections.singletonList(empleadoDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/empleado"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Gaston"));
    }

    @Test
    void createEmpleado_deberiaCrearEmpleado() throws Exception {
        when(empleadoService.createEmpleado(any(EmpleadoDTO.class))).thenReturn(empleadoDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/empleado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleadoDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Gaston"));
    }

    @Test
    void putEmpleado_deberiaActualizarEmpleado() throws Exception {
        when(empleadoService.updateEmpleado(anyInt(), any(EmpleadoDTO.class))).thenReturn(empleadoDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/empleado/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleadoDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Gaston"));
    }
}
