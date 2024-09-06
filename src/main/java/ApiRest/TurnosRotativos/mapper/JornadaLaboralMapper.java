package ApiRest.TurnosRotativos.mapper;

import ApiRest.TurnosRotativos.dto.JornadaLaboralDTO;
import ApiRest.TurnosRotativos.entity.ConceptoLaboral;
import ApiRest.TurnosRotativos.entity.Empleado;
import ApiRest.TurnosRotativos.entity.JornadaLaboral;

public class JornadaLaboralMapper {

    public static JornadaLaboralDTO toDTO(JornadaLaboral jornadaLaboral) {
        JornadaLaboralDTO dto = new JornadaLaboralDTO();
        dto.setId(jornadaLaboral.getId());
        dto.setFecha(jornadaLaboral.getFecha());
        dto.setHsTrabajadas(jornadaLaboral.getHsTrabajadas());

        if (jornadaLaboral.getEmpleado() != null) {
            dto.setNombreCompleto(jornadaLaboral.getEmpleado().getNombre() + " "+
                    jornadaLaboral.getEmpleado().getApellido());
            dto.setNroDocumento(jornadaLaboral.getEmpleado().getNroDocumento());
        }

        if (jornadaLaboral.getConceptoLaboral() != null) {
            dto.setConcepto(jornadaLaboral.getConceptoLaboral().getNombre());
        }

        return dto;
    }

    public static JornadaLaboral toEntity(JornadaLaboralDTO dto, Empleado empleado, ConceptoLaboral conceptoLaboral) {
        JornadaLaboral jornadaLaboral = new JornadaLaboral();
        jornadaLaboral.setFecha(dto.getFecha());
        jornadaLaboral.setHsTrabajadas(dto.getHsTrabajadas());
        jornadaLaboral.setEmpleado(empleado);
        jornadaLaboral.setConceptoLaboral(conceptoLaboral);

        return jornadaLaboral;
    }
}
