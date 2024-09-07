package ApiRest.TurnosRotativos.service.impl;

import ApiRest.TurnosRotativos.dto.JornadaLaboralDTO;
import ApiRest.TurnosRotativos.entity.JornadaLaboral;
import ApiRest.TurnosRotativos.repository.JornadaLaboralRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JornadaLaboralServiceImplTest {

    @Mock
    private JornadaLaboralRepository jornadaLaboralRepository;

    @InjectMocks
    private JornadaLaboralServiceImpl jornadaLaboralService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void obtenerJornadasConFechasYDocumento() {

        String fechaDesde = "2024-01-01";
        String fechaHasta = "2024-01-31";
        String nroDocumento = "12345";
        LocalDate fechaDesdeDate = LocalDate.parse(fechaDesde);
        LocalDate fechaHastaDate = LocalDate.parse(fechaHasta);

        JornadaLaboral jornadaLaboral = new JornadaLaboral();
        List<JornadaLaboral> jornadas = Collections.singletonList(jornadaLaboral);
        when(jornadaLaboralRepository.findByEmpleadoNroDocumentoAndFechaBetween(eq(12345), eq(fechaDesdeDate), eq(fechaHastaDate)))
                .thenReturn(jornadas);

        List<JornadaLaboralDTO> resultado = jornadaLaboralService.getJornadas(fechaDesde, fechaHasta, nroDocumento);

        assertEquals(1, resultado.size());
        verify(jornadaLaboralRepository).findByEmpleadoNroDocumentoAndFechaBetween(eq(12345), eq(fechaDesdeDate), eq(fechaHastaDate));
    }

    @Test
    public void obtenerJornadasConFechasSinDocumento() {

        String fechaDesde = "2024-01-01";
        String fechaHasta = "2024-01-31";
        LocalDate fechaDesdeDate = LocalDate.parse(fechaDesde);
        LocalDate fechaHastaDate = LocalDate.parse(fechaHasta);

        JornadaLaboral jornadaLaboral = new JornadaLaboral();
        List<JornadaLaboral> jornadas = Collections.singletonList(jornadaLaboral);
        when(jornadaLaboralRepository.findByFechaBetween(eq(fechaDesdeDate), eq(fechaHastaDate)))
                .thenReturn(jornadas);

        List<JornadaLaboralDTO> resultado = jornadaLaboralService.getJornadas(fechaDesde, fechaHasta, null);

        assertEquals(1, resultado.size());
        verify(jornadaLaboralRepository).findByFechaBetween(eq(fechaDesdeDate), eq(fechaHastaDate));
    }

    @Test
    public void obtenerJornadasConDocumentoSinFechas() {

        String nroDocumento = "12345";
        Integer nroDocumentoInt = 12345;

        JornadaLaboral jornadaLaboral = new JornadaLaboral();
        List<JornadaLaboral> jornadas = Collections.singletonList(jornadaLaboral);
        when(jornadaLaboralRepository.findByEmpleadoNroDocumento(eq(nroDocumentoInt)))
                .thenReturn(jornadas);

        List<JornadaLaboralDTO> resultado = jornadaLaboralService.getJornadas(null, null, nroDocumento);

        assertEquals(1, resultado.size());
        verify(jornadaLaboralRepository).findByEmpleadoNroDocumento(eq(nroDocumentoInt));
    }

    @Test
    public void obtenerJornadasConFechaDesdeYSinFechaHasta() {

        String fechaDesde = "2024-01-01";
        LocalDate fechaDesdeDate = LocalDate.parse(fechaDesde);

        JornadaLaboral jornadaLaboral = new JornadaLaboral();
        List<JornadaLaboral> jornadas = Collections.singletonList(jornadaLaboral);
        when(jornadaLaboralRepository.findByFechaAfterOrEqual(eq(fechaDesdeDate)))
                .thenReturn(jornadas);

        List<JornadaLaboralDTO> resultado = jornadaLaboralService.getJornadas(fechaDesde, null, null);

        assertEquals(1, resultado.size());
        verify(jornadaLaboralRepository).findByFechaAfterOrEqual(eq(fechaDesdeDate));
    }

    @Test
    public void obtenerJornadasConFechaHastaYSinFechaDesde() {

        String fechaHasta = "2024-01-31";
        LocalDate fechaHastaDate = LocalDate.parse(fechaHasta);

        JornadaLaboral jornadaLaboral = new JornadaLaboral();
        List<JornadaLaboral> jornadas = Collections.singletonList(jornadaLaboral);
        when(jornadaLaboralRepository.findByFechaBeforeOrEqual(eq(fechaHastaDate)))
                .thenReturn(jornadas);

        List<JornadaLaboralDTO> resultado = jornadaLaboralService.getJornadas(null, fechaHasta, null);

        assertEquals(1, resultado.size());
        verify(jornadaLaboralRepository).findByFechaBeforeOrEqual(eq(fechaHastaDate));
    }

    @Test
    public void obtenerJornadasSinFechasNiDocumento() {

        JornadaLaboral jornadaLaboral = new JornadaLaboral();
        List<JornadaLaboral> jornadas = Collections.singletonList(jornadaLaboral);
        when(jornadaLaboralRepository.findAll())
                .thenReturn(jornadas);

        List<JornadaLaboralDTO> resultado = jornadaLaboralService.getJornadas(null, null, null);

        assertEquals(1, resultado.size());
        verify(jornadaLaboralRepository).findAll();
    }
}
