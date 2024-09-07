package ApiRest.TurnosRotativos.service;

import ApiRest.TurnosRotativos.dto.ConceptoLaboralDTO;

import java.util.List;

public interface ConceptoLaboralService {

    public List<ConceptoLaboralDTO> getConceptosLaborales(Long id, String nombre);
}
