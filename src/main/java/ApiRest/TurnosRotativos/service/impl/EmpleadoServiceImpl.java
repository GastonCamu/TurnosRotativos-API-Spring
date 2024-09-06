package ApiRest.TurnosRotativos.service.impl;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.entity.Empleado;
import ApiRest.TurnosRotativos.exception.BusinessException;
import ApiRest.TurnosRotativos.mapper.EmpleadoMapper;
import ApiRest.TurnosRotativos.repository.EmpleadoRepository;
import ApiRest.TurnosRotativos.repository.JornadaLaboralRepository;
import ApiRest.TurnosRotativos.service.EmpleadoService;
import ApiRest.TurnosRotativos.service.validation.EmpleadoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    EmpleadoRepository repository;

    @Autowired
    JornadaLaboralRepository jornadaRepo;

    @Override
    public EmpleadoDTO getEmpleado(int id) {
        Optional<Empleado> empleado = this.repository.findById(id);
        if (empleado.isPresent()) {
            return EmpleadoMapper.toDTO(empleado.get());
        } else {
            throw new BusinessException("No se encontró el empleado con Id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<EmpleadoDTO> getEmpleados() {
        List<Empleado> empleados = this.repository.findAll();
        return empleados.stream()
                .map(EmpleadoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmpleadoDTO createEmpleado(EmpleadoDTO empleadoDTO) {

        EmpleadoValidator.validateUniqueFields(empleadoDTO, repository);
        EmpleadoValidator.validateFechas(empleadoDTO);
        EmpleadoValidator.validateEdad(empleadoDTO);

        Empleado empleado = EmpleadoMapper.toEntity(empleadoDTO);
        empleado = this.repository.save(empleado);
        return EmpleadoMapper.toDTO(empleado);
    }

    @Override
    @Transactional
    public EmpleadoDTO updateEmpleado(int id, EmpleadoDTO empleadoDTO) {
        Empleado existingEmpleado = repository.findById(id)
                .orElseThrow(() -> new BusinessException("No se encontró el empleado con Id: " + id, HttpStatus.NOT_FOUND));

        EmpleadoValidator.validateUniqueFieldsForUpdate(empleadoDTO, existingEmpleado, repository);
        EmpleadoValidator.validateFechas(empleadoDTO);
        EmpleadoValidator.validateEdad(empleadoDTO);


        existingEmpleado.setNombre(empleadoDTO.getNombre());
        existingEmpleado.setApellido(empleadoDTO.getApellido());
        existingEmpleado.setEmail(empleadoDTO.getEmail());
        existingEmpleado.setNroDocumento(empleadoDTO.getNroDocumento());
        existingEmpleado.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
        existingEmpleado.setFechaIngreso(empleadoDTO.getFechaIngreso());

        Empleado updatedEmpleado = repository.save(existingEmpleado);

        return  EmpleadoMapper.toDTO(updatedEmpleado);
    }

    @Override
    @Transactional
    public void deleteEmpleado(Integer id) {
        Empleado empleado = repository.findById(id)
                .orElseThrow(() -> new BusinessException("No se encontró el empleado con Id: " + id, HttpStatus.NOT_FOUND));

        EmpleadoValidator.validateExistsJornadaByEmpleadoId(jornadaRepo, id);

        repository.deleteById(id);

    }


}
