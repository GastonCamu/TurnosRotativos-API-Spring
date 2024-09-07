package ApiRest.TurnosRotativos.service.validation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ApiRest.TurnosRotativos.dto.JornadaLaboralDTO;
import ApiRest.TurnosRotativos.exception.BusinessException;
import ApiRest.TurnosRotativos.repository.ConceptoLaboralRepository;
import ApiRest.TurnosRotativos.repository.EmpleadoRepository;
import ApiRest.TurnosRotativos.repository.JornadaLaboralRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

public class JornadaLaboralValidatorTest {

    @Mock
    private EmpleadoRepository empleadoRepositoryMock;

    @Mock
    private ConceptoLaboralRepository conceptoLaboralRepositoryMock;

    @Mock
    private JornadaLaboralRepository jornadaLaboralRepositoryMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidarExistenciaEmpleado_EmpleadoNoEncontrado() {

        when(empleadoRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            JornadaLaboralValidator.validateEmpleadoExistence(1L, empleadoRepositoryMock);
        });
        assertEquals("No existe el empleado ingresado.", exception.getMessage());
    }

    @Test
    void testValidateHsTrabajadasByConcepto_HsTrabajadasObligatorio() {

        JornadaLaboralDTO jornadaDTO = new JornadaLaboralDTO();
        jornadaDTO.setIdConcepto(1L);
        jornadaDTO.setHsTrabajadas(null);

        when(conceptoLaboralRepositoryMock.findNombreById(1L)).thenReturn("Turno Normal");

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            JornadaLaboralValidator.validateHsTrabajadasByConcepto(jornadaDTO, conceptoLaboralRepositoryMock);
        });
        assertEquals("'hsTrabajadas' es obligatorio para el concepto ingresado.", exception.getMessage());
    }

    @Test
    void testValidarLimiteHorasDiarias_SuperaLimite() {

        JornadaLaboralDTO jornadaDTO = new JornadaLaboralDTO();
        jornadaDTO.setIdEmpleado(1L);
        jornadaDTO.setHsTrabajadas(10);
        jornadaDTO.setFecha(LocalDate.of(2024, 9, 5));

        when(jornadaLaboralRepositoryMock.sumHsTrabajadasByEmpleadoIdAndFecha(1L, LocalDate.of(2024, 9, 5)))
                .thenReturn(10);


        BusinessException exception = assertThrows(BusinessException.class, () -> {
            JornadaLaboralValidator.validateDailyHoursLimit(jornadaLaboralRepositoryMock, LocalDate.of(2024, 9, 5), 1L, 10);
        });
        assertEquals("Un empleado no puede cargar más de 14 horas trabajadas en un día.", exception.getMessage());
    }

    @Test
    void testValidarLimiteTurnosLibresPorSemana_SuperaLimite() {

        JornadaLaboralDTO jornadaDTO = new JornadaLaboralDTO();
        jornadaDTO.setIdEmpleado(1L);
        jornadaDTO.setFecha(LocalDate.of(2024, 9, 2));

        when(jornadaLaboralRepositoryMock.countLibreShiftByEmpleadoIdAndWeek(1L, LocalDate.of(2024, 9, 2).with(DayOfWeek.MONDAY), LocalDate.of(2024, 9, 2).with(DayOfWeek.SUNDAY)))
                .thenReturn(2);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            JornadaLaboralValidator.validateLibreShiftsLimitPerWeek(jornadaLaboralRepositoryMock, LocalDate.of(2024, 9, 2), 1L);
        });
        assertEquals("El empleado no cuenta con más días libres esta semana.", exception.getMessage());
    }

    @Test
    void testValidarExistenciaConceptoLaboral_ConceptoNoEncontrado() {
        when(conceptoLaboralRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            JornadaLaboralValidator.validateConceptoLaboralExistence(1L, conceptoLaboralRepositoryMock);
        });
        assertEquals("No existe el concepto ingresado.", exception.getMessage());
    }

    @Test
    void testValidateHorasTrabajadas_HorasDentroDeRango() {

        Integer hsTrabajadas = 10;
        Integer hsMinimo = 4;
        Integer hsMaximo = 12;

        assertDoesNotThrow(() -> JornadaLaboralValidator.validateHorasTrabajadas(hsTrabajadas, hsMinimo, hsMaximo));
    }

}
