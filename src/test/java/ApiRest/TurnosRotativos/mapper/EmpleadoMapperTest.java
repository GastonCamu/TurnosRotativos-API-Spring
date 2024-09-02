package ApiRest.TurnosRotativos.mapper;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.entity.Empleado;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

public class EmpleadoMapperTest {

    @Test
    void dadoEmpleadoCuandoConvertirADTOEntoncesCorrecto() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setId(1);
        empleado.setNroDocumento(32444321);
        empleado.setNombre("Gaston");
        empleado.setApellido("Jouse");
        empleado.setEmail("gastonjouse@gmail.com");
        empleado.setFechaNacimiento(LocalDate.of(2002, 9, 1));
        empleado.setFechaIngreso(LocalDate.of(2023, 9, 1));
        // fechaCreacion no se establece aquí porque es administrada por la base de datos.

        EmpleadoDTO dto = EmpleadoMapper.toDTO(empleado);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(empleado.getId());
        assertThat(dto.getNroDocumento()).isEqualTo(empleado.getNroDocumento());
        assertThat(dto.getNombre()).isEqualTo(empleado.getNombre());
        assertThat(dto.getApellido()).isEqualTo(empleado.getApellido());
        assertThat(dto.getEmail()).isEqualTo(empleado.getEmail());
        assertThat(dto.getFechaNacimiento()).isEqualTo(empleado.getFechaNacimiento());
        assertThat(dto.getFechaIngreso()).isEqualTo(empleado.getFechaIngreso());
        // Nota: No se verifica fechaCreacion ya que no está en la entidad durante el test.
    }

    @Test
    void dadoEmpleadoDTOCuandoConvertirAEntidadEntoncesCorrecto() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setNroDocumento(32444321);
        dto.setNombre("Gaston");
        dto.setApellido("Jouse");
        dto.setEmail("gastonjouse@gmail.com");
        dto.setFechaNacimiento(LocalDate.of(2002, 9, 1));
        dto.setFechaIngreso(LocalDate.of(2023, 9, 1));

        Empleado empleado = EmpleadoMapper.toEntity(dto);

        assertThat(empleado).isNotNull();
        assertThat(empleado.getNroDocumento()).isEqualTo(dto.getNroDocumento());
        assertThat(empleado.getNombre()).isEqualTo(dto.getNombre());
        assertThat(empleado.getApellido()).isEqualTo(dto.getApellido());
        assertThat(empleado.getEmail()).isEqualTo(dto.getEmail());
        assertThat(empleado.getFechaNacimiento()).isEqualTo(dto.getFechaNacimiento());
        assertThat(empleado.getFechaIngreso()).isEqualTo(dto.getFechaIngreso());
    }

    @Test
    void dadoEmpleadoNuloCuandoConvertirADTOEntoncesNulo() {

        EmpleadoDTO dto = EmpleadoMapper.toDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    void dadoEmpleadoDTONuloCuandoConvertirAEntidadEntoncesNulo() {

        Empleado empleado = EmpleadoMapper.toEntity(null);

        assertThat(empleado).isNull();
    }
}