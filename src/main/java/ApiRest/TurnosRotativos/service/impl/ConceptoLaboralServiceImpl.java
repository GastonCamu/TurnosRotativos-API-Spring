package ApiRest.TurnosRotativos.service.impl;

import ApiRest.TurnosRotativos.dto.ConceptoLaboralDTO;
import ApiRest.TurnosRotativos.entity.ConceptoLaboral;
import ApiRest.TurnosRotativos.mapper.ConceptoLaboralMapper;
import ApiRest.TurnosRotativos.repository.ConceptoLaboralRepository;
import ApiRest.TurnosRotativos.service.ConceptoLaboralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConceptoLaboralServiceImpl implements ConceptoLaboralService {

    @Autowired
    ConceptoLaboralRepository repository;

    @Override
    public List<ConceptoLaboralDTO> getConceptosLaborales(Integer id, String nombre) {
        List<ConceptoLaboral> conceptosLaborales;

        if (id != null && nombre != null) {
            conceptosLaborales = repository.findByIdAndNombreContainingIgnoreCase(id, nombre);
        } else if (id != null) {
            Optional<ConceptoLaboral> optional = repository.findById(id);
            conceptosLaborales = optional.map(List::of).orElse(List.of());
        } else if (nombre != null) {
            conceptosLaborales = repository.findByNombreContainingIgnoreCase(nombre);
        } else {
            conceptosLaborales = repository.findAll();
        }

        return conceptosLaborales.stream()
                .map(ConceptoLaboralMapper::toDTO)
                .collect(Collectors.toList());
    }
}
