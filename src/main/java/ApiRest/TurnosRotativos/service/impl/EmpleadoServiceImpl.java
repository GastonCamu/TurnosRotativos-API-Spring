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

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    EmpleadoRepository repository;

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
