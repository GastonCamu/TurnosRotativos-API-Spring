package ApiRest.TurnosRotativos.service;

import ApiRest.TurnosRotativos.dto.JornadaLaboralDTO;
import ApiRest.TurnosRotativos.entity.JornadaLaboral;

import java.time.LocalDate;

public interface JornadaLaboralService {

    JornadaLaboralDTO createJornadaLaboral(JornadaLaboralDTO jornadaDTO);
}
