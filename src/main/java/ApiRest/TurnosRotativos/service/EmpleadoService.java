package ApiRest.TurnosRotativos.service;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;

import java.util.List;

public interface EmpleadoService {

    EmpleadoDTO getEmpleado(Long id);

    List<EmpleadoDTO> getEmpleados();

    EmpleadoDTO createEmpleado(EmpleadoDTO empleadoDTO);

    EmpleadoDTO updateEmpleado(Long id, EmpleadoDTO empleadoDTO);

    void deleteEmpleado(Long id);
}
