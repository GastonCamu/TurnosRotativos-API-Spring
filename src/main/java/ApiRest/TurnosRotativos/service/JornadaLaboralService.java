package ApiRest.TurnosRotativos.service;

import ApiRest.TurnosRotativos.dto.JornadaLaboralDTO;
import ApiRest.TurnosRotativos.entity.JornadaLaboral;

import java.time.LocalDate;
import java.util.List;

public interface JornadaLaboralService {

    List<JornadaLaboralDTO> getJornadas(String fechaDesde, String fechaHasta, String nroDocumento);

    JornadaLaboralDTO createJornadaLaboral(JornadaLaboralDTO jornadaDTO);
}
