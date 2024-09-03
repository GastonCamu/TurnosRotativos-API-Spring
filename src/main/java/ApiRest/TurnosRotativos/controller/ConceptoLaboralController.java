package ApiRest.TurnosRotativos.controller;

import ApiRest.TurnosRotativos.dto.ConceptoLaboralDTO;
import ApiRest.TurnosRotativos.service.ConceptoLaboralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/concepto")
public class ConceptoLaboralController {

    @Autowired
    ConceptoLaboralService conceptoLaboralService;

    @GetMapping
    public ResponseEntity<List<ConceptoLaboralDTO>> getConceptosLaborales(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nombre) {

        List<ConceptoLaboralDTO> conceptosLaborales = this.conceptoLaboralService.getConceptosLaborales(id, nombre);
        return ResponseEntity.ok(conceptosLaborales);
    }
}
