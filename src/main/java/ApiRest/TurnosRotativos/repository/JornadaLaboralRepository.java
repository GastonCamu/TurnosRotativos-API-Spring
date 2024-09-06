package ApiRest.TurnosRotativos.repository;

import ApiRest.TurnosRotativos.entity.JornadaLaboral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JornadaLaboralRepository extends JpaRepository<JornadaLaboral, Integer> {

    @Query("SELECT SUM(j.hsTrabajadas) " +
            "FROM JornadaLaboral j " +
            "WHERE j.empleado.id = :idEmpleado AND j.fecha = :fecha")
    Integer sumHsTrabajadasByEmpleadoIdAndFecha(
            @Param("idEmpleado") Integer idEmpleado,
            @Param("fecha") LocalDate fecha
    );

    boolean existsByEmpleadoIdAndConceptoLaboralIdAndFecha(int empleadoId, int ConceptoLaboralId, LocalDate fecha);

    @Query("SELECT COALESCE(SUM(j.hsTrabajadas), 0) " +
            "FROM JornadaLaboral j " +
            "WHERE j.empleado.id = :empleadoId AND j.fecha BETWEEN :startOfWeek AND :endOfWeek")
    Integer sumHsTrabajadasByEmpleadoIdAndWeek(
            @Param("empleadoId") Integer empleadoId,
            @Param("startOfWeek") LocalDate startOfWeek,
            @Param("endOfWeek") LocalDate endOfWeek
    );

    @Query("SELECT COALESCE(SUM(j.hsTrabajadas), 0) " +
            "FROM JornadaLaboral j " +
            "WHERE j.empleado.id = :empleadoId AND j.fecha BETWEEN :startOfMonth AND :endOfMonth")
    Integer sumHsTrabajadasByEmpleadoIdAndMonth(
            @Param("empleadoId") Integer empleadoId,
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
            @Param("empleadoId") int empleadoId,
            @Param("fecha") LocalDate fecha
    );

    @Query("SELECT COUNT(j) " +
            "FROM JornadaLaboral j " +
            "WHERE j.empleado.id = :empleadoId " +
            "AND j.conceptoLaboral.nombre = 'Turno Extra' " +
            "AND j.fecha BETWEEN :startOfWeek " +
            "AND :endOfWeek")
    int countExtraShiftByEmpleadoIdAndWeek(
            @Param("empleadoId") int empleadoId,
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
            @Param("empleadoId") int empleadoId,
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
            @Param("empleadoId") int empleadoId,
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
            @Param("empleadoId") int empleadoId,
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
}
