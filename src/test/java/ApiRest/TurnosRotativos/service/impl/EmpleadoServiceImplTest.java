package ApiRest.TurnosRotativos.service.impl;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.entity.Empleado;
import ApiRest.TurnosRotativos.exception.BusinessException;
import ApiRest.TurnosRotativos.repository.EmpleadoRepository;
import ApiRest.TurnosRotativos.repository.JornadaLaboralRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceImplTest {

    private EmpleadoDTO empleadoDTO;
    private Empleado existingEmpleado;

    @Mock
    private EmpleadoRepository empleadoRepositoryMock;
    @Mock
    private JornadaLaboralRepository jornadaRepoMock;

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

        existingEmpleado = new Empleado();
        existingEmpleado.setId(1L);
        existingEmpleado.setNombre("Gaston");
        existingEmpleado.setApellido("Perez");
        existingEmpleado.setNroDocumento(32444321);
        existingEmpleado.setEmail("gastonjouse@gmail.com");
        existingEmpleado.setFechaNacimiento(LocalDate.of(2002, 9, 1));
        existingEmpleado.setFechaIngreso(LocalDate.of(2023, 9, 1));
    }

    @Test
    void getEmpleado_deberiaLanzarExcepcion_cuandoEmpleadoNoExiste() {

        when(empleadoRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> empleadoServiceImplUnderTest.getEmpleado(1L));

        assertEquals("No se encontró el empleado con Id: 1", exception.getMessage());
    }

    @Test
    void getEmpleados_deberiaRetornarListaVacia_cuandoNoHayEmpleados() {

        when(empleadoRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        List<EmpleadoDTO> resultado = empleadoServiceImplUnderTest.getEmpleados();

        assertEquals(0, resultado.size(), "La lista debería estar vacía");
    }

    @Test
    void crearEmpleado_deberiaLanzarExcepcion_cuandoElEmailYaExiste() {

        when(empleadoRepositoryMock.existsByEmail(any())).thenReturn(true);

        Throwable exception = assertThrows(BusinessException.class,
                () -> empleadoServiceImplUnderTest.createEmpleado(empleadoDTO));

        assertEquals("Ya existe un empleado con el email ingresado.", exception.getMessage());
    }

    @Test
    void crearEmpleado_deberiaLanzarExcepcion_cuandoElNroDocumentoYaExiste() {

        when(empleadoRepositoryMock.existsByNroDocumento(anyInt())).thenReturn(true);

        Throwable exception = assertThrows(BusinessException.class,
                () -> empleadoServiceImplUnderTest.createEmpleado(empleadoDTO));

        assertEquals("Ya existe un empleado con el documento ingresado.", exception.getMessage());
    }

    @Test
    void crearEmpleado_deberiaLanzarExcepcion_cuandoLaFechaDeIngresoEsPosteriorALaActual() {

        empleadoDTO.setFechaIngreso(LocalDate.now().plusDays(1));

        Throwable exception = assertThrows(BusinessException.class,
                () -> empleadoServiceImplUnderTest.createEmpleado(empleadoDTO));

        assertEquals("La fecha de ingreso no puede ser posterior al día de la fecha.", exception.getMessage());
    }

    @Test
    void crearEmpleado_deberiaLanzarExcepcion_cuandoLaFechaDeNacimientoEsPosteriorALaActual() {

        empleadoDTO.setFechaNacimiento(LocalDate.now().plusDays(1));

        Throwable exception = assertThrows(BusinessException.class,
                () -> empleadoServiceImplUnderTest.createEmpleado(empleadoDTO));

        assertEquals("La fecha de nacimiento no puede ser posterior al día de la fecha.", exception.getMessage());
    }

    @Test
    void crearEmpleado_deberiaLanzarExcepcion_cuandoEdadEsMenorA18() {

        empleadoDTO.setFechaNacimiento(LocalDate.now());

        Throwable exception = assertThrows(BusinessException.class,
                () -> empleadoServiceImplUnderTest.createEmpleado(empleadoDTO));

        assertEquals("La edad del empleado no puede ser menor a 18 años.", exception.getMessage());
    }

    @Test
    void updateEmpleado_deberiaLanzarExcepcion_cuandoLaFechaDeIngresoEsPosteriorALaActual() {

        empleadoDTO.setFechaIngreso(LocalDate.now().plusDays(1));

        when(empleadoRepositoryMock.findById(anyLong())).thenReturn(Optional.of(existingEmpleado));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> empleadoServiceImplUnderTest.updateEmpleado(1L, empleadoDTO));

        assertEquals("La fecha de ingreso no puede ser posterior al día de la fecha.", exception.getMessage());
    }

    @Test
    void updateEmpleado_deberiaLanzarExcepcion_cuandoLaFechaDeNacimientoEsPosteriorALaActual() {

        empleadoDTO.setFechaNacimiento(LocalDate.now().plusDays(1));

        when(empleadoRepositoryMock.findById(anyLong())).thenReturn(Optional.of(existingEmpleado));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> empleadoServiceImplUnderTest.updateEmpleado(1L, empleadoDTO));

        assertEquals("La fecha de nacimiento no puede ser posterior al día de la fecha.", exception.getMessage());
    }

    @Test
    void updateEmpleado_deberiaLanzarExcepcion_cuandoEdadEsMenorA18() {

        empleadoDTO.setFechaNacimiento(LocalDate.now());

        when(empleadoRepositoryMock.findById(anyLong())).thenReturn(Optional.of(existingEmpleado));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> empleadoServiceImplUnderTest.updateEmpleado(1L, empleadoDTO));

        assertEquals("La edad del empleado no puede ser menor a 18 años.", exception.getMessage());
    }

    @Test
    public void testDeleteEmpleado_NotFound() {

        Long id = 1L;

        when(empleadoRepositoryMock.findById(id)).thenReturn(Optional.empty());

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            empleadoServiceImplUnderTest.deleteEmpleado(id);
        });

        assertEquals("No se encontró el empleado con Id: " + id, thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    public void testDeleteEmpleado_WithJornadas() {

        Long id = 1L;
        Empleado empleado = new Empleado();

        when(empleadoRepositoryMock.findById(id)).thenReturn(Optional.of(empleado));
        when(jornadaRepoMock.existsByEmpleadoId(id)).thenReturn(true);

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            empleadoServiceImplUnderTest.deleteEmpleado(id);
        });

        assertEquals("No es posible eliminar un empleado con jornadas asociadas.", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
    }

    @Test
    public void testDeleteEmpleado_Success() {

        Long id = 1L;
        Empleado empleado = new Empleado();

        when(empleadoRepositoryMock.findById(id)).thenReturn(Optional.of(empleado));
        when(jornadaRepoMock.existsByEmpleadoId(id)).thenReturn(false);

        empleadoServiceImplUnderTest.deleteEmpleado(id);

        verify(empleadoRepositoryMock).deleteById(id);
    }
}
