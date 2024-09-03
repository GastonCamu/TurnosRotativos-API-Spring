package ApiRest.TurnosRotativos.controller;

import ApiRest.TurnosRotativos.dto.ConceptoLaboralDTO;
import ApiRest.TurnosRotativos.service.ConceptoLaboralService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(ConceptoLaboralController.class)
public class ConceptoLaboralControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConceptoLaboralService conceptoLaboralService;

    @Autowired
    private ObjectMapper objectMapper;

    private ConceptoLaboralDTO conceptoLaboralDTO1;
    private ConceptoLaboralDTO conceptoLaboralDTO2;

    @BeforeEach
    public void setUp() {
        conceptoLaboralDTO1 = new ConceptoLaboralDTO();
        conceptoLaboralDTO1.setId(1);
        conceptoLaboralDTO1.setNombre("Concepto 1");
        conceptoLaboralDTO1.setLaborable(true);
        conceptoLaboralDTO1.setHsMinimo(5);
        conceptoLaboralDTO1.setHsMaximo(10);

        conceptoLaboralDTO2 = new ConceptoLaboralDTO();
        conceptoLaboralDTO2.setId(2);
        conceptoLaboralDTO2.setNombre("Concepto 2");
        conceptoLaboralDTO2.setLaborable(false);
        conceptoLaboralDTO2.setHsMinimo(3);
        conceptoLaboralDTO2.setHsMaximo(8);
    }

    @Test
    public void testGetConceptosLaborales() throws Exception {
        List<ConceptoLaboralDTO> conceptos = Arrays.asList(conceptoLaboralDTO1, conceptoLaboralDTO2);

        when(conceptoLaboralService.getConceptosLaborales(null, null)).thenReturn(conceptos);

        mockMvc.perform(MockMvcRequestBuilders.get("/concepto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Concepto 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nombre").value("Concepto 2"));
    }

    @Test
    public void testGetConceptosLaboralesById() throws Exception {
        when(conceptoLaboralService.getConceptosLaborales(1, null)).thenReturn(Arrays.asList(conceptoLaboralDTO1));

        mockMvc.perform(MockMvcRequestBuilders.get("/concepto")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Concepto 1"));
    }

    @Test
    public void testGetConceptosLaboralesByNombre() throws Exception {
        when(conceptoLaboralService.getConceptosLaborales(null, "Concepto 1")).thenReturn(Arrays.asList(conceptoLaboralDTO1));

        mockMvc.perform(MockMvcRequestBuilders.get("/concepto")
                        .param("nombre", "Concepto 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Concepto 1"));
    }

    @Test
    public void testGetConceptosLaboralesByIdAndNombre() throws Exception {
        when(conceptoLaboralService.getConceptosLaborales(1, "Concepto 1")).thenReturn(Arrays.asList(conceptoLaboralDTO1));

        mockMvc.perform(MockMvcRequestBuilders.get("/concepto")
                        .param("id", "1")
                        .param("nombre", "Concepto 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Concepto 1"));
    }
}
