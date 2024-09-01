package ApiRest.TurnosRotativos.service;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.entity.Empleado;

import java.util.List;

public interface EmpleadoService {
//    public EmpleadoDTO getEmpleado(Long id);
//    public List<EmpleadoDTO> getEmpleados();
    public EmpleadoDTO createEmpleado(EmpleadoDTO empleadoDTO);
//    public void deleteEmpleado(Long id);
}
