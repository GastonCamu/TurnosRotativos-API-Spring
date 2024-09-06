package ApiRest.TurnosRotativos.service.impl;

import ApiRest.TurnosRotativos.dto.JornadaLaboralDTO;
import ApiRest.TurnosRotativos.entity.ConceptoLaboral;
import ApiRest.TurnosRotativos.entity.Empleado;
import ApiRest.TurnosRotativos.entity.JornadaLaboral;
import ApiRest.TurnosRotativos.mapper.JornadaLaboralMapper;
import ApiRest.TurnosRotativos.repository.ConceptoLaboralRepository;
import ApiRest.TurnosRotativos.repository.EmpleadoRepository;
import ApiRest.TurnosRotativos.repository.JornadaLaboralRepository;
import ApiRest.TurnosRotativos.service.JornadaLaboralService;
import ApiRest.TurnosRotativos.service.validation.JornadaLaboralValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JornadaLaboralServiceImpl implements JornadaLaboralService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    ConceptoLaboralRepository conceptoLaboralRepository;

    @Autowired
    JornadaLaboralRepository jornadaLaboralRepository;

    @Override
    @Transactional
    public JornadaLaboralDTO createJornadaLaboral(JornadaLaboralDTO jornadaDTO) {

        JornadaLaboralValidator.validateHsTrabajadasByConcepto(jornadaDTO, conceptoLaboralRepository);
        JornadaLaboralValidator.validateRNJornadas(jornadaDTO, conceptoLaboralRepository, jornadaLaboralRepository);

        Empleado empleado = JornadaLaboralValidator
                .validateEmpleadoExistence(jornadaDTO.getIdEmpleado(), empleadoRepository);

        ConceptoLaboral conceptoLaboral = JornadaLaboralValidator
                .validateConceptoLaboralExistence(jornadaDTO.getIdConcepto(), conceptoLaboralRepository);

        JornadaLaboral jornadaLaboral = JornadaLaboralMapper.toEntity(jornadaDTO, empleado, conceptoLaboral);
        jornadaLaboral = jornadaLaboralRepository.save(jornadaLaboral);

        return JornadaLaboralMapper.toDTO(jornadaLaboral);
    }
}
