package ApiRest.TurnosRotativos.controller;

import ApiRest.TurnosRotativos.dto.JornadaLaboralDTO;
import ApiRest.TurnosRotativos.service.JornadaLaboralService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/jornada")
public class JornadaLaboralController {

    @Autowired
    private JornadaLaboralService jornadaLaboralService;

    @GetMapping
    public ResponseEntity<List<JornadaLaboralDTO>> getJornadas(
        @RequestParam(required = false) String nroDocumento,
        @RequestParam(required = false) String fechaDesde,
        @RequestParam(required = false) String fechaHasta) {

        List<JornadaLaboralDTO> jornadas = jornadaLaboralService.getJornadas(fechaDesde, fechaHasta, nroDocumento);
        return ResponseEntity.ok(jornadas);
    }
    @PostMapping
    public ResponseEntity<JornadaLaboralDTO> createJornada(@Valid @RequestBody JornadaLaboralDTO jornadaDTO) {
        JornadaLaboralDTO jornada = this.jornadaLaboralService.createJornadaLaboral(jornadaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(jornada);
    }
}
