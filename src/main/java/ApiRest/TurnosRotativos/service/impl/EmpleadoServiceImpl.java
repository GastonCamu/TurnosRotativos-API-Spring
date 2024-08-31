package ApiRest.TurnosRotativos.service.impl;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.entity.Empleado;
import ApiRest.TurnosRotativos.exception.BusinessException;
import ApiRest.TurnosRotativos.mapper.EmpleadoMapper;
import ApiRest.TurnosRotativos.repository.EmpleadoRepository;
import ApiRest.TurnosRotativos.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    EmpleadoRepository repository;

    @Override
    public EmpleadoDTO CreateEmpleado(EmpleadoDTO empleadoDTO) {
        int edad = Period.between(empleadoDTO.getFechaNacimiento(), LocalDate.now()).getYears();
        if (edad < 18) {
            throw new BusinessException("La edad del empleado no puede ser menor a 18 años.", HttpStatus.BAD_REQUEST);
        }
        if (repository.existsByNroDocumento(empleadoDTO.getNroDocumento())) {
            throw new BusinessException("Ya existe un empleado con el documento ingresado.", HttpStatus.CONFLICT);
        }
        if (repository.existsByEmail(empleadoDTO.getEmail())) {
            throw new BusinessException("Ya existe un empleado con el email ingresado.", HttpStatus.CONFLICT);
        }
        if (empleadoDTO.getFechaIngreso().isAfter(LocalDate.now())) {
            throw new BusinessException("La fecha de ingreso no puede ser posterior al día de la fecha.", HttpStatus.BAD_REQUEST);
        }
        if (empleadoDTO.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new BusinessException("La fecha de nacimiento no puede ser posterior al día de la fecha.", HttpStatus.BAD_REQUEST);
        }
        Empleado empleado = EmpleadoMapper.toEntity(empleadoDTO);
        empleado = this.repository.save(empleado);
        return EmpleadoMapper.toDTO(empleado);
    }
}
