package ApiRest.TurnosRotativos.mapper;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.entity.Empleado;

public class EmpleadoMapper {

    public static EmpleadoDTO toDTO(Empleado empleado) {
        if (empleado == null) {
            return null;
        }

        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(empleado.getId());
        dto.setNroDocumento(empleado.getNroDocumento());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setEmail(empleado.getEmail());
        dto.setFechaNacimiento(empleado.getFechaNacimiento());
        dto.setFechaIngreso(empleado.getFechaIngreso());
        dto.setFechaCreacion(empleado.getFechaCreacion());

        return dto;
    }

    public static Empleado toEntity(EmpleadoDTO dto) {
        if (dto == null) {
            return null;
        }

        Empleado empleado = new Empleado();
        empleado.setNroDocumento(dto.getNroDocumento());
        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setEmail(dto.getEmail());
        empleado.setFechaNacimiento(dto.getFechaNacimiento());
        empleado.setFechaIngreso(dto.getFechaIngreso());

        return empleado;
    }
}
