package ApiRest.TurnosRotativos.mapper;

import ApiRest.TurnosRotativos.dto.ConceptoLaboralDTO;
import ApiRest.TurnosRotativos.entity.ConceptoLaboral;

public class ConceptoLaboralMapper {
    public static ConceptoLaboralDTO toDTO(ConceptoLaboral conceptoLaboral) {

        if (conceptoLaboral == null) {
            return null;
        }

        ConceptoLaboralDTO dto = new ConceptoLaboralDTO();
        dto.setId(conceptoLaboral.getId());
        dto.setNombre(conceptoLaboral.getNombre());
        dto.setLaborable(conceptoLaboral.getLaborable());
        dto.setHsMinimo(conceptoLaboral.getHsMinimo());
        dto.setHsMaximo(conceptoLaboral.getHsMaximo());

        return dto;
    }

    public static ConceptoLaboral toEntity(ConceptoLaboralDTO dto) {

        if (dto == null) {
            return null;
        }
        ConceptoLaboral conceptoLaboral = new ConceptoLaboral();
        conceptoLaboral.setId(dto.getId());
        conceptoLaboral.setNombre(dto.getNombre());
        conceptoLaboral.setLaborable(dto.getLaborable());

        if (dto.getHsMinimo() != null) {
            conceptoLaboral.setHsMinimo(dto.getHsMinimo());
        }

        if (dto.getHsMaximo() != null) {
            conceptoLaboral.setHsMaximo(dto.getHsMaximo());
        }

        return conceptoLaboral;
    }
}
