package ApiRest.TurnosRotativos.service.validation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ApiRest.TurnosRotativos.dto.JornadaLaboralDTO;
import ApiRest.TurnosRotativos.entity.ConceptoLaboral;
import ApiRest.TurnosRotativos.exception.BusinessException;
import ApiRest.TurnosRotativos.repository.ConceptoLaboralRepository;
import ApiRest.TurnosRotativos.repository.EmpleadoRepository;
import ApiRest.TurnosRotativos.repository.JornadaLaboralRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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

    @InjectMocks
    private JornadaLaboralValidator jornadaLaboralValidatorUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateEmpleadoExistence_EmpleadoNotFound() {
        // Arrange
        when(empleadoRepositoryMock.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            JornadaLaboralValidator.validateEmpleadoExistence(1, empleadoRepositoryMock);
        });
        assertEquals("No existe el empleado ingresado.", exception.getMessage());
    }

    @Test
    void testValidateHsTrabajadasByConcepto_HsTrabajadasObligatorio() {
        // Arrange
        JornadaLaboralDTO jornadaDTO = new JornadaLaboralDTO();
        jornadaDTO.setIdConcepto(1);
        jornadaDTO.setHsTrabajadas(null);

        when(conceptoLaboralRepositoryMock.findNombreById(1)).thenReturn("Turno Normal");

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            JornadaLaboralValidator.validateHsTrabajadasByConcepto(jornadaDTO, conceptoLaboralRepositoryMock);
        });
        assertEquals("'hsTrabajadas' es obligatorio para el concepto ingresado.", exception.getMessage());
    }

    @Test
    void testValidateDailyHoursLimit_ExceedsLimit() {
        // Arrange
        JornadaLaboralDTO jornadaDTO = new JornadaLaboralDTO();
        jornadaDTO.setIdEmpleado(1);
        jornadaDTO.setHsTrabajadas(10);
        jornadaDTO.setFecha(LocalDate.of(2024, 9, 5));

        when(jornadaLaboralRepositoryMock.sumHsTrabajadasByEmpleadoIdAndFecha(1, LocalDate.of(2024, 9, 5)))
                .thenReturn(10); // Total es 20, lo cual excede el límite de 14

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            JornadaLaboralValidator.validateDailyHoursLimit(jornadaLaboralRepositoryMock, LocalDate.of(2024, 9, 5), 1, 10);
        });
        assertEquals("Un empleado no puede cargar más de 14 horas trabajadas en un día.", exception.getMessage());
    }

    @Test
    void testValidateLibreShiftsLimitPerWeek_ExceedsLimit() {
        // Arrange
        JornadaLaboralDTO jornadaDTO = new JornadaLaboralDTO();
        jornadaDTO.setIdEmpleado(1);
        jornadaDTO.setFecha(LocalDate.of(2024, 9, 2)); // Monday

        when(jornadaLaboralRepositoryMock.countLibreShiftByEmpleadoIdAndWeek(1, LocalDate.of(2024, 9, 2).with(DayOfWeek.MONDAY), LocalDate.of(2024, 9, 2).with(DayOfWeek.SUNDAY)))
                .thenReturn(2); // Total es 3, lo cual excede el límite de 2

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            JornadaLaboralValidator.validateLibreShiftsLimitPerWeek(jornadaLaboralRepositoryMock, LocalDate.of(2024, 9, 2), 1);
        });
        assertEquals("El empleado no cuenta con más días libres esta semana.", exception.getMessage());
    }

    @Test
    void testValidateConceptoLaboralExistence_ConceptoNotFound() {
        // Arrange
        when(conceptoLaboralRepositoryMock.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            JornadaLaboralValidator.validateConceptoLaboralExistence(1, conceptoLaboralRepositoryMock);
        });
        assertEquals("No existe el concepto ingresado.", exception.getMessage());
    }

    @Test
    void testValidateConceptoLaboralExistence_ConceptoFound() {
        // Arrange
        ConceptoLaboral concepto = new ConceptoLaboral();
        when(conceptoLaboralRepositoryMock.findById(1)).thenReturn(Optional.of(concepto));

        // Act
        ConceptoLaboral result = JornadaLaboralValidator.validateConceptoLaboralExistence(1, conceptoLaboralRepositoryMock);

        // Assert
        assertNotNull(result);
        assertEquals(concepto, result);
    }

    @Test
    void testValidateHorasTrabajadas_HorasDentroDeRango() {
        // Arrange
        Integer hsTrabajadas = 10;
        Integer hsMinimo = 4;
        Integer hsMaximo = 12;

        // Act & Assert
        assertDoesNotThrow(() -> JornadaLaboralValidator.validateHorasTrabajadas(hsTrabajadas, hsMinimo, hsMaximo));
    }

}
