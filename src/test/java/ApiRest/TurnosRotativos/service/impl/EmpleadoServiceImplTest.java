package ApiRest.TurnosRotativos.service.impl;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.exception.BusinessException;
import ApiRest.TurnosRotativos.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceImplTest {

    private EmpleadoDTO empleadoDTO;

    @Mock
    private EmpleadoRepository empleadoRepositoryMock;

    @InjectMocks
    private EmpleadoServiceImpl empleadoServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setNombre("Gaston");
        empleadoDTO.setApellido("jouse");
        empleadoDTO.setNroDocumento(32444321);
        empleadoDTO.setEmail("gastonjouse@gmail.com");
        empleadoDTO.setFechaNacimiento(LocalDate.of(2002, 9, 1));
        empleadoDTO.setFechaIngreso(LocalDate.of(2023, 9, 1));
    }

    // Test para probar si existe otro empleado con el mismo correo electronico
    @Test
    void crearEmpleado_deberiaLanzarExcepcion_cuandoElEmailYaExiste() {
        when(empleadoRepositoryMock.existsByEmail(any())).thenReturn(true);

        Throwable exception = assertThrows(BusinessException.class, () -> empleadoServiceImplUnderTest.createEmpleado(empleadoDTO));

        assertEquals("Ya existe un empleado con el email ingresado.", exception.getMessage());
    }

    // Test para probar si existe otro empleado con el mismo numero de documento
    @Test
    void crearEmpleado_deberiaLanzarExcepcion_cuandoElNroDocumentoYaExiste() {
        when(empleadoRepositoryMock.existsByNroDocumento(anyInt())).thenReturn(true);

        Throwable exception = assertThrows(BusinessException.class, () -> empleadoServiceImplUnderTest.createEmpleado(empleadoDTO));

        assertEquals("Ya existe un empleado con el documento ingresado.", exception.getMessage());
    }

    // Test para probar que la fecha de ingreso no sea posterior a la actual
    @Test
    void crearEmpleado_deberiaLanzarExcepcion_cuandoLaFechaDeIngresoEsPosteriorALaActual() {
        empleadoDTO.setFechaIngreso(LocalDate.now().plusDays(1));

        Throwable exception = assertThrows(BusinessException.class, () -> empleadoServiceImplUnderTest.createEmpleado(empleadoDTO));

        assertEquals("La fecha de ingreso no puede ser posterior al día de la fecha.", exception.getMessage());
    }

    // Test para probar que la fecha de nacimiento no sea posterior a la actual
    @Test
    void crearEmpleado_deberiaLanzarExcepcion_cuandoLaFechaDeNacimientoEsPosteriorALaActual() {
        empleadoDTO.setFechaNacimiento(LocalDate.now().plusDays(1));

        Throwable exception = assertThrows(BusinessException.class, () -> empleadoServiceImplUnderTest.createEmpleado(empleadoDTO));

        assertEquals("La fecha de nacimiento no puede ser posterior al día de la fecha.", exception.getMessage());
    }

    // Test para probar si el empleado tiene una edad menor a 18 años
    @Test
    void crearEmpleado_deberiaLanzarExcepcion_cuandoEdadEsMenorA18() {
        empleadoDTO.setFechaNacimiento(LocalDate.now());

        Throwable exception = assertThrows(BusinessException.class, () -> empleadoServiceImplUnderTest.createEmpleado(empleadoDTO));

        assertEquals("La edad del empleado no puede ser menor a 18 años.", exception.getMessage());
    }
}
