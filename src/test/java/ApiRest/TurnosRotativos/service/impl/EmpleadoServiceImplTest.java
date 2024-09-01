package ApiRest.TurnosRotativos.service.impl;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.exception.BusinessException;
import ApiRest.TurnosRotativos.mapper.EmpleadoMapper;
import ApiRest.TurnosRotativos.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        empleadoDTO.setNombre("Gaston");
        empleadoDTO.setApellido("jouse");
        empleadoDTO.setNroDocumento(32444321);
        empleadoDTO.setEmail("gastonjouse@gmail.com");
        empleadoDTO.setFechaNacimiento(LocalDate.of(2002, 9, 1));
        empleadoDTO.setFechaIngreso(LocalDate.of(2023, 9, 1));
    }

    // Test para verificar que lanza una excepción si el empleado no existe
    @Test
    void getEmpleado_deberiaLanzarExcepcion_cuandoEmpleadoNoExiste() {
        when(empleadoRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> empleadoServiceImplUnderTest.getEmpleado(1));

        assertEquals("No se encontró el empleado con Id: 1", exception.getMessage());
    }

    // Test para verificar que se retorna una lista vacía cuando no hay empleados
    @Test
    void getEmpleados_deberiaRetornarListaVacia_cuandoNoHayEmpleados() {
        when(empleadoRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        List<EmpleadoDTO> resultado = empleadoServiceImplUnderTest.getEmpleados();

        assertEquals(0, resultado.size(), "La lista debería estar vacía");
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
