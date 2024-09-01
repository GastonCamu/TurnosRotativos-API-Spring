package ApiRest.TurnosRotativos.service.impl;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.entity.Empleado;
import ApiRest.TurnosRotativos.mapper.EmpleadoMapper;
import ApiRest.TurnosRotativos.repository.EmpleadoRepository;
import ApiRest.TurnosRotativos.service.EmpleadoService;
import ApiRest.TurnosRotativos.service.validation.EmpleadoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    EmpleadoRepository repository;

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


}
