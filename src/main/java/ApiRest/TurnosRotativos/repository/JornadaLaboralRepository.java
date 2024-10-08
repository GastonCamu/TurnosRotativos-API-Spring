package ApiRest.TurnosRotativos.repository;

import ApiRest.TurnosRotativos.entity.JornadaLaboral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JornadaLaboralRepository extends JpaRepository<JornadaLaboral, Long> {

    @Query("SELECT SUM(j.hsTrabajadas) " +
            "FROM JornadaLaboral j " +
            "WHERE j.empleado.id = :idEmpleado AND j.fecha = :fecha")
    Integer sumHsTrabajadasByEmpleadoIdAndFecha(
            @Param("idEmpleado") Long idEmpleado,
            @Param("fecha") LocalDate fecha
    );

    boolean existsByEmpleadoIdAndConceptoLaboralIdAndFecha(
            Long empleadoId,
            Long ConceptoLaboralId,
            LocalDate fecha);

    @Query("SELECT COALESCE(SUM(j.hsTrabajadas), 0) " +
            "FROM JornadaLaboral j " +
            "WHERE j.empleado.id = :empleadoId AND j.fecha BETWEEN :startOfWeek AND :endOfWeek")
    Integer sumHsTrabajadasByEmpleadoIdAndWeek(
            @Param("empleadoId") Long empleadoId,
            @Param("startOfWeek") LocalDate startOfWeek,
            @Param("endOfWeek") LocalDate endOfWeek
    );

    @Query("SELECT COALESCE(SUM(j.hsTrabajadas), 0) " +
            "FROM JornadaLaboral j " +
            "WHERE j.empleado.id = :empleadoId AND j.fecha BETWEEN :startOfMonth AND :endOfMonth")
    Integer sumHsTrabajadasByEmpleadoIdAndMonth(
            @Param("empleadoId") Long empleadoId,
            @Param("startOfMonth") LocalDate startOfMonth,
            @Param("endOfMonth") LocalDate endOfMonth
    );

    @Query("SELECT COUNT(j) > 0 " +
            "FROM JornadaLaboral j " +
            "WHERE j.empleado.id = :empleadoId " +
            "AND j.fecha = :fecha " +
            "AND j.conceptoLaboral.id IN (" +
            "   SELECT c.id " +
            "   FROM ConceptoLaboral c " +
            "   WHERE c.nombre = 'Día Libre'" +
            ")")
    boolean existsDiaLibreByEmpleadoIdAndFecha(
            @Param("empleadoId") Long empleadoId,
            @Param("fecha") LocalDate fecha
    );

    @Query("SELECT COUNT(j) " +
            "FROM JornadaLaboral j " +
            "WHERE j.empleado.id = :empleadoId " +
            "AND j.conceptoLaboral.nombre = 'Turno Extra' " +
            "AND j.fecha BETWEEN :startOfWeek " +
            "AND :endOfWeek")
    int countExtraShiftByEmpleadoIdAndWeek(
            @Param("empleadoId") Long empleadoId,
            @Param("startOfWeek") LocalDate startOfWeek,
            @Param("endOfWeek") LocalDate endOfWeek
    );

    @Query("SELECT COUNT(j) " +
            "FROM JornadaLaboral j " +
            "WHERE j.empleado.id = :empleadoId " +
            "AND j.conceptoLaboral.nombre = 'Turno Normal' " +
            "AND j.fecha BETWEEN :startOfWeek " +
            "AND :endOfWeek")
    int countNormalShiftByEmpleadoIdAndWeek(
            @Param("empleadoId") Long empleadoId,
            @Param("startOfWeek") LocalDate startOfWeek,
            @Param("endOfWeek") LocalDate endOfWeek
    );

    @Query("SELECT COUNT(j) " +
            "FROM JornadaLaboral j " +
            "WHERE j.empleado.id = :empleadoId " +
            "AND j.conceptoLaboral.nombre = 'Día Libre' " +
            "AND j.fecha BETWEEN :startOfWeek " +
            "AND :endOfWeek")
    int countLibreShiftByEmpleadoIdAndWeek(
            @Param("empleadoId") Long empleadoId,
            @Param("startOfWeek") LocalDate startOfWeek,
            @Param("endOfWeek") LocalDate endOfWeek
    );

    @Query("SELECT COUNT(j) " +
            "FROM JornadaLaboral j " +
            "WHERE j.empleado.id = :empleadoId " +
            "AND j.conceptoLaboral.nombre = 'Día Libre' " +
            "AND j.fecha BETWEEN :startOfMonth " +
            "AND :endOfMonth")
    int countLibreShiftByEmpleadoIdAndMonth(
            @Param("empleadoId") Long empleadoId,
            @Param("startOfMonth") LocalDate startOfMonth,
            @Param("endOfMonth") LocalDate endOfMonth
    );

    @Query("SELECT COUNT(j) " +
            "FROM JornadaLaboral j " +
            "WHERE j.conceptoLaboral.id = (" +
            "SELECT c.id FROM ConceptoLaboral c " +
            "WHERE c.nombre = :nombreConcepto) " +
            "AND j.fecha = :fecha")
    int countEmpleadosByConceptoNombreAndFecha(
            @Param("nombreConcepto") String nombreConcepto,
            @Param("fecha") LocalDate fecha
    );

    List<JornadaLaboral> findByEmpleadoNroDocumento(Integer nroDocumento);

    List<JornadaLaboral> findByFechaBetween(LocalDate fechaDesde, LocalDate fechaHasta);

    List<JornadaLaboral> findByEmpleadoNroDocumentoAndFechaBetween(Integer nroDocumento, LocalDate fechaDesde, LocalDate fechaHasta);

    @Query("SELECT j FROM JornadaLaboral j WHERE j.fecha >= :fechaDesde")
    List<JornadaLaboral> findByFechaAfterOrEqual(LocalDate fechaDesde);

    @Query("SELECT j FROM JornadaLaboral j WHERE j.fecha <= :fechaHasta")
    List<JornadaLaboral> findByFechaBeforeOrEqual(LocalDate fechaHasta);

    @Query("SELECT j " +
            "FROM JornadaLaboral j " +
            "WHERE j.empleado.nroDocumento = :nroDocumento " +
            "AND j.fecha >= :fechaDesde")
    List<JornadaLaboral> findByEmpleadoNroDocumentoAndFechaAfterOrEqual(
            @Param("nroDocumento") Integer nroDocumento,
            @Param("fechaDesde") LocalDate fechaDesde);

    @Query("SELECT j " +
            "FROM JornadaLaboral j " +
            "WHERE j.empleado.nroDocumento = :nroDocumento " +
            "AND j.fecha <= :fechaHasta")
    List<JornadaLaboral> findByEmpleadoNroDocumentoAndFechaBeforeOrEqual(
            @Param("nroDocumento") Integer nroDocumento,
            @Param("fechaHasta") LocalDate fechaHasta);

    boolean existsByEmpleadoId(Long empleadoId);
}
