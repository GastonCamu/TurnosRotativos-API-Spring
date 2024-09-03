package ApiRest.TurnosRotativos.service.impl;
import ApiRest.TurnosRotativos.dto.ConceptoLaboralDTO;
import ApiRest.TurnosRotativos.entity.ConceptoLaboral;
import ApiRest.TurnosRotativos.repository.ConceptoLaboralRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ConceptoLaboralServicioImplTest {

    @Mock
    private ConceptoLaboralRepository repositorio;

    @InjectMocks
    private ConceptoLaboralServiceImpl conceptoLaboralServicio;

    private ConceptoLaboral conceptoLaboral;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        conceptoLaboral = new ConceptoLaboral();
        conceptoLaboral.setId(1);
        conceptoLaboral.setNombre("Concepto");
        conceptoLaboral.setLaborable(true);
        conceptoLaboral.setHsMinimo(5);
        conceptoLaboral.setHsMaximo(10);
    }

    @Test
    public void testObtenerConceptosLaboralesConIdYNombre() {

        Integer id = 1;
        String nombre = "Concepto";
        when(repositorio.findByIdAndNombreContainingIgnoreCase(id, nombre)).thenReturn(List.of(conceptoLaboral));

        List<ConceptoLaboralDTO> resultado = conceptoLaboralServicio.getConceptosLaborales(id, nombre);

        assertEquals(1, resultado.size());
        ConceptoLaboralDTO dto = resultado.get(0);
        assertEquals(id, dto.getId());
        assertEquals(nombre, dto.getNombre());
        assertEquals(5, dto.getHsMinimo());
        assertEquals(10, dto.getHsMaximo());
    }

    @Test
    public void testObtenerConceptosLaboralesConIdSolo() {

        Integer id = 1;
        when(repositorio.findById(id)).thenReturn(Optional.of(conceptoLaboral));

        List<ConceptoLaboralDTO> resultado = conceptoLaboralServicio.getConceptosLaborales(id, null);

        assertEquals(1, resultado.size());
        ConceptoLaboralDTO dto = resultado.get(0);
        assertEquals(id, dto.getId());
        assertEquals("Concepto", dto.getNombre());
        assertEquals(5, dto.getHsMinimo());
        assertEquals(10, dto.getHsMaximo());
    }

    @Test
    public void testObtenerConceptosLaboralesConNombreSolo() {

        String nombre = "Concepto";
        when(repositorio.findByNombreContainingIgnoreCase(nombre)).thenReturn(List.of(conceptoLaboral));

        List<ConceptoLaboralDTO> resultado = conceptoLaboralServicio.getConceptosLaborales(null, nombre);

        assertEquals(1, resultado.size());
        ConceptoLaboralDTO dto = resultado.get(0);
        assertEquals(1, dto.getId());
        assertEquals(nombre, dto.getNombre());
        assertEquals(5, dto.getHsMinimo());
        assertEquals(10, dto.getHsMaximo());
    }

    @Test
    public void testObtenerConceptosLaboralesSinFiltros() {

        when(repositorio.findAll()).thenReturn(List.of(conceptoLaboral));

        List<ConceptoLaboralDTO> resultado = conceptoLaboralServicio.getConceptosLaborales(null, null);

        assertEquals(1, resultado.size());
        ConceptoLaboralDTO dto = resultado.get(0);
        assertEquals(1, dto.getId());
        assertEquals("Concepto", dto.getNombre());
        assertEquals(5, dto.getHsMinimo());
        assertEquals(10, dto.getHsMaximo());
    }
}