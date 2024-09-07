package ApiRest.TurnosRotativos.mapper;

import ApiRest.TurnosRotativos.dto.ConceptoLaboralDTO;
import ApiRest.TurnosRotativos.entity.ConceptoLaboral;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ConceptoLaboralMapperTest {

    @Test
    public void testToDTO() {

        ConceptoLaboral conceptoLaboral = new ConceptoLaboral();
        conceptoLaboral.setId(1L);
        conceptoLaboral.setNombre("Concepto");
        conceptoLaboral.setLaborable(true);
        conceptoLaboral.setHsMinimo(5);
        conceptoLaboral.setHsMaximo(10);

        ConceptoLaboralDTO dto = ConceptoLaboralMapper.toDTO(conceptoLaboral);

        assertEquals(1, dto.getId());
        assertEquals("Concepto", dto.getNombre());
        assertEquals(true, dto.getLaborable());
        assertEquals(5, dto.getHsMinimo());
        assertEquals(10, dto.getHsMaximo());
    }

    @Test
    public void testToDTOWithNull() {

        ConceptoLaboralDTO dto = ConceptoLaboralMapper.toDTO(null);

        assertNull(dto);
    }

    @Test
    public void testToEntity() {

        ConceptoLaboralDTO dto = new ConceptoLaboralDTO();
        dto.setId(1L);
        dto.setNombre("Concepto");
        dto.setLaborable(true);
        dto.setHsMinimo(5);
        dto.setHsMaximo(10);

        ConceptoLaboral conceptoLaboral = ConceptoLaboralMapper.toEntity(dto);

        assertEquals(1, conceptoLaboral.getId());
        assertEquals("Concepto", conceptoLaboral.getNombre());
        assertEquals(true, conceptoLaboral.getLaborable());
        assertEquals(5, conceptoLaboral.getHsMinimo());
        assertEquals(10, conceptoLaboral.getHsMaximo());
    }

    @Test
    public void testToEntityWithNull() {

        ConceptoLaboral conceptoLaboral = ConceptoLaboralMapper.toEntity(null);

        assertNull(conceptoLaboral);
    }
}