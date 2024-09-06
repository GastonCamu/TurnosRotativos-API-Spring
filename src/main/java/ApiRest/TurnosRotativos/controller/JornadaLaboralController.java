package ApiRest.TurnosRotativos.controller;

import ApiRest.TurnosRotativos.dto.JornadaLaboralDTO;
import ApiRest.TurnosRotativos.service.JornadaLaboralService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jornada")
public class JornadaLaboralController {

    @Autowired
    private JornadaLaboralService jornadaLaboralService;

    @PostMapping
    public ResponseEntity<JornadaLaboralDTO> createJornada(@Valid @RequestBody JornadaLaboralDTO jornadaDTO) {
        JornadaLaboralDTO jornada = this.jornadaLaboralService.createJornadaLaboral(jornadaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(jornada);
    }
}
