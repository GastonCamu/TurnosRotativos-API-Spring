package ApiRest.TurnosRotativos.service;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.entity.Empleado;

import java.util.List;

public interface EmpleadoService {
    EmpleadoDTO getEmpleado(int id);
    List<EmpleadoDTO> getEmpleados();
    EmpleadoDTO createEmpleado(EmpleadoDTO empleadoDTO);
    EmpleadoDTO updateEmpleado(int id, EmpleadoDTO empleadoDTO);
    void deleteEmpleado(Integer id);
}
