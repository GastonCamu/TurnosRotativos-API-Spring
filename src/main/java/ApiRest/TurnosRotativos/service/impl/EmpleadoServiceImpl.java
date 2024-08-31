package ApiRest.TurnosRotativos.service.impl;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.entity.Empleado;
import ApiRest.TurnosRotativos.mapper.EmpleadoMapper;
import ApiRest.TurnosRotativos.repository.EmpleadoRepository;
import ApiRest.TurnosRotativos.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    EmpleadoRepository repository;

    @Override
    public EmpleadoDTO CreateEmpleado(EmpleadoDTO empleadoDTO) {
        Empleado empleado = EmpleadoMapper.toEntity(empleadoDTO);
        empleado = this.repository.save(empleado);
        return EmpleadoMapper.toDTO(empleado);
    }
}
