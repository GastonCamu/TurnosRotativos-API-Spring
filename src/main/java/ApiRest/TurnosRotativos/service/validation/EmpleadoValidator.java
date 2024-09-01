package ApiRest.TurnosRotativos.service.validation;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.exception.BusinessException;
import ApiRest.TurnosRotativos.repository.EmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

public class EmpleadoValidator {

    public static void validateEdad(EmpleadoDTO empleadoDTO) {
        int edad = Period.between(empleadoDTO.getFechaNacimiento(), LocalDate.now()).getYears();
        if (edad < 18) {
            throw new BusinessException("La edad del empleado no puede ser menor a 18 años.", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateFechas(EmpleadoDTO empleadoDTO) {
        if (empleadoDTO.getFechaIngreso().isAfter(LocalDate.now())) {
            throw new BusinessException("La fecha de ingreso no puede ser posterior al día de la fecha.", HttpStatus.BAD_REQUEST);
        }
        if (empleadoDTO.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new BusinessException("La fecha de nacimiento no puede ser posterior al día de la fecha.", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateUniqueFields(EmpleadoDTO empleadoDTO, EmpleadoRepository repository) {
        if (repository.existsByNroDocumento(empleadoDTO.getNroDocumento())) {
            throw new BusinessException("Ya existe un empleado con el documento ingresado.", HttpStatus.CONFLICT);
        }
        if (repository.existsByEmail(empleadoDTO.getEmail())) {
            throw new BusinessException("Ya existe un empleado con el email ingresado.", HttpStatus.CONFLICT);
        }
    }

}
