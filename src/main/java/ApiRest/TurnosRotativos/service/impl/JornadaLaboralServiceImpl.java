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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JornadaLaboralServiceImpl implements JornadaLaboralService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    ConceptoLaboralRepository conceptoLaboralRepository;

    @Autowired
    JornadaLaboralRepository jornadaLaboralRepository;

    @Override
    public List<JornadaLaboralDTO> getJornadas(String fechaDesde, String fechaHasta, String nroDocumento) {

        List<JornadaLaboral> jornadas;

        Integer nroDocumentoInt = JornadaLaboralValidator.convertToInteger(nroDocumento);

        LocalDate fechaDesdeDate = JornadaLaboralValidator.convertToLocalDate(fechaDesde);
        LocalDate fechaHastaDate = JornadaLaboralValidator.convertToLocalDate(fechaHasta);

        if (nroDocumentoInt != null && fechaDesdeDate != null && fechaHastaDate != null) {
            JornadaLaboralValidator.validateFechas(fechaDesdeDate, fechaHastaDate);
            jornadas = jornadaLaboralRepository.findByEmpleadoNroDocumentoAndFechaBetween(nroDocumentoInt, fechaDesdeDate, fechaHastaDate);

        } else if (nroDocumentoInt != null && fechaDesdeDate != null) {
            jornadas = jornadaLaboralRepository.findByEmpleadoNroDocumentoAndFechaAfterOrEqual(nroDocumentoInt, fechaDesdeDate);

        } else if (nroDocumentoInt != null && fechaHastaDate != null) {
            jornadas = jornadaLaboralRepository.findByEmpleadoNroDocumentoAndFechaBeforeOrEqual(nroDocumentoInt, fechaHastaDate);

        } else if (nroDocumentoInt != null) {
            jornadas = jornadaLaboralRepository.findByEmpleadoNroDocumento(nroDocumentoInt);

        } else if (fechaDesdeDate != null && fechaHastaDate != null) {
            JornadaLaboralValidator.validateFechas(fechaDesdeDate, fechaHastaDate);
            jornadas = jornadaLaboralRepository.findByFechaBetween(fechaDesdeDate, fechaHastaDate);

        } else if (fechaDesdeDate != null) {
            jornadas = jornadaLaboralRepository.findByFechaAfterOrEqual(fechaDesdeDate);

        } else if (fechaHastaDate != null) {
            jornadas = jornadaLaboralRepository.findByFechaBeforeOrEqual(fechaHastaDate);

        } else {
            jornadas = jornadaLaboralRepository.findAll();
        }

        return jornadas.stream()
                .map(JornadaLaboralMapper::toDTO)
                .collect(Collectors.toList());
    }

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
