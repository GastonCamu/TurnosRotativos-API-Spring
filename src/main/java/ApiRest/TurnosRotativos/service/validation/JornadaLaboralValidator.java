package ApiRest.TurnosRotativos.service.validation;

import ApiRest.TurnosRotativos.dto.JornadaLaboralDTO;
import ApiRest.TurnosRotativos.entity.ConceptoLaboral;
import ApiRest.TurnosRotativos.entity.Empleado;
import ApiRest.TurnosRotativos.exception.BusinessException;
import ApiRest.TurnosRotativos.repository.ConceptoLaboralRepository;
import ApiRest.TurnosRotativos.repository.EmpleadoRepository;
import ApiRest.TurnosRotativos.repository.JornadaLaboralRepository;
import org.springframework.http.HttpStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;


public class JornadaLaboralValidator {

    public static LocalDate convertToLocalDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new BusinessException("El formato de la fecha es inválido. Use el formato yyyy-mm-dd");
        }
    }

    public static Integer convertToInteger(String nroDocumento) {
        if (nroDocumento == null) {
            return null;
        }
        try {
            return Integer.parseInt(nroDocumento);

        } catch (NumberFormatException e) {
            throw new BusinessException("El campo 'nroDocumento' solo puede contener números enteros.");
        }
    }

    public static void validateFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        if (fechaDesde.isAfter(fechaHasta)) {
            throw new BusinessException("El campo 'fechaDesde' no puede ser mayor que 'fechaHasta'.");
        }
    }

    public static Empleado validateEmpleadoExistence(
            int idEmpleado,
            EmpleadoRepository empleadoRepository) {

        return empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new BusinessException("No existe el empleado ingresado.",
                        HttpStatus.NOT_FOUND));
    }

    public static ConceptoLaboral validateConceptoLaboralExistence(
            int idConcepto,
            ConceptoLaboralRepository conceptoLaboralRepository) {

        return conceptoLaboralRepository.findById(idConcepto)
                .orElseThrow(() -> new BusinessException("No existe el concepto ingresado.",
                        HttpStatus.NOT_FOUND));
    }

    public static void validateHsTrabajadasByConcepto(
            JornadaLaboralDTO jornadaDTO,
            ConceptoLaboralRepository conceptoLaboralRepository) {

        String concepto = conceptoLaboralRepository.findNombreById(jornadaDTO.getIdConcepto());

        if (("Turno Normal".equals(concepto) || "Turno Extra".equals(concepto)) && jornadaDTO.getHsTrabajadas() == null) {
            throw new BusinessException("'hsTrabajadas' es obligatorio para el concepto ingresado.");
        }

        if ("Día Libre".equals(concepto) && jornadaDTO.getHsTrabajadas() != null) {
            throw new BusinessException("El concepto ingresado no requiere el ingreso de 'hsTrabajadas'");
        }
    }

    public static void validateRNJornadas(
            JornadaLaboralDTO jornadaDTO,
            ConceptoLaboralRepository conceptoRepo,
            JornadaLaboralRepository jornadaRepo) {

        Optional<ConceptoLaboral> concepto = conceptoRepo.findById(jornadaDTO.getIdConcepto());

        if (concepto.isPresent()) {
            String conceptoNombre = concepto.get().getNombre();
            Integer hsTrabajadas = jornadaDTO.getHsTrabajadas();
            Integer hsMinimo = concepto.get().getHsMinimo();
            Integer hsMaximo = concepto.get().getHsMaximo();
            Integer idEmpleado = jornadaDTO.getIdEmpleado();
            Integer idConcepto = jornadaDTO.getIdConcepto();
            LocalDate fecha = jornadaDTO.getFecha();

            // RN 10
            validateConceptLimitPerDay(jornadaRepo, conceptoNombre, fecha);

            // RN 11
            validateNoDuplicateConceptPerDay(jornadaRepo,idEmpleado, idConcepto, fecha);

            if(validateTurnos(conceptoNombre)) {

                // RN 1
                validateHorasTrabajadas(hsTrabajadas, hsMinimo, hsMaximo);

                // RN 2
                validateDailyHoursLimit(jornadaRepo, fecha, idEmpleado, hsTrabajadas);

                // RN 3
                validateWeeklyHoursLimit(jornadaRepo, fecha, idEmpleado, hsTrabajadas);

                // RN 4
                validateMonthYHoursLimit(jornadaRepo, fecha, idEmpleado, hsTrabajadas);

                // RN 5
                validateDayOff(jornadaRepo, idEmpleado, fecha);

                // RN 6
                validateExtraShiftsLimitPerWeek(jornadaRepo, fecha, idEmpleado);

                // RN 7
                validateNormalShiftsLimitPerWeek(jornadaRepo, fecha, idEmpleado);

            } else {
                // RN 8
                validateLibreShiftsLimitPerWeek(jornadaRepo, fecha, idEmpleado);

                // RN 9
                validateLibreShiftsLimitPerMonth(jornadaRepo,fecha,idEmpleado);
            }
        }

    }
    private static boolean validateTurnos(String conceptoNombre) {
        return (conceptoNombre.equals("Turno Normal") || conceptoNombre.equals("Turno Extra"));
    }
    private static LocalDate getWeekStart(LocalDate fecha) {
        return fecha.with(DayOfWeek.MONDAY);
    }
    private static LocalDate getWeekEnd(LocalDate fecha) {
        return fecha.with(DayOfWeek.SUNDAY);
    }

    static void validateHorasTrabajadas(
            Integer hsTrabajadas,
            Integer hsMinimo,
            Integer hsMaximo) {

        if (hsTrabajadas > hsMaximo || hsTrabajadas < hsMinimo) {
            throw new BusinessException("El rango de horas que se puede cargar para este concepto es de " +
                    hsMinimo + " - "+ hsMaximo);
        }
    }

    static void validateDailyHoursLimit(
            JornadaLaboralRepository jornadaRepo,
            LocalDate fecha,
            Integer idEmpleado,
            Integer hsTrabajadas) {

        Integer hsTrabajadasEnBD = jornadaRepo.sumHsTrabajadasByEmpleadoIdAndFecha(idEmpleado, fecha);

        if (hsTrabajadasEnBD == null) {
            hsTrabajadasEnBD = 0;
        }

        int hsTotalesTrabajadas = hsTrabajadas + hsTrabajadasEnBD;

        if (hsTotalesTrabajadas > 14) {
            throw new BusinessException("Un empleado no puede cargar más de 14 horas trabajadas en un día.");
        }
    }

    private static void validateWeeklyHoursLimit(
            JornadaLaboralRepository jornadaRepo,
            LocalDate fecha,
            Integer idEmpleado,
            Integer hsTrabajadas) {

        LocalDate startOfWeek = getWeekStart(fecha);
        LocalDate endOfWeek = getWeekEnd(fecha);

        Integer hsSemanales =  jornadaRepo.sumHsTrabajadasByEmpleadoIdAndWeek(idEmpleado, startOfWeek, endOfWeek);

        if (hsSemanales + hsTrabajadas > 52) {
            throw new BusinessException("El empleado ingresado supera las 52 horas semanales.");
        }
    }

    private static void validateMonthYHoursLimit(
            JornadaLaboralRepository jornadaRepo,
            LocalDate fecha,
            Integer idEmpleado,
            Integer hsTrabajadas) {

        YearMonth yearMonth = YearMonth.of(fecha.getYear(), fecha.getMonth());
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();

        Integer hsMensuales = jornadaRepo.sumHsTrabajadasByEmpleadoIdAndMonth(idEmpleado, startOfMonth, endOfMonth);

        if (hsMensuales + hsTrabajadas > 190) {
            throw new BusinessException("El empleado ingresado supera las 190 horas mensuales.");
        }

    }


    private static void validateDayOff(
            JornadaLaboralRepository jornadaRepo,
            Integer idEmpleado,
            LocalDate fecha) {

        if (jornadaRepo.existsDiaLibreByEmpleadoIdAndFecha(idEmpleado, fecha)) {
            throw new BusinessException("El empleado ingresado cuenta con un día libre en esa fecha.");
        }
    }

    private static void validateExtraShiftsLimitPerWeek(
            JornadaLaboralRepository jornadaRepo,
            LocalDate fecha,
            Integer idEmpleado) {

        LocalDate startOfWeek = getWeekStart(fecha);
        LocalDate endOfWeek = getWeekEnd(fecha);
        int countExtraShifts = jornadaRepo.countExtraShiftByEmpleadoIdAndWeek(idEmpleado, startOfWeek, endOfWeek);

        if (countExtraShifts >= 3) {
            throw new BusinessException("El empleado ingresado ya cuenta con 3 turnos extra esta semana.");
        }
    }

    private static void validateNormalShiftsLimitPerWeek(
            JornadaLaboralRepository jornadaRepo,
            LocalDate fecha,
            Integer idEmpleado) {

        LocalDate startOfWeek = getWeekStart(fecha);
        LocalDate endOfWeek = getWeekEnd(fecha);
        int countNormalShifts = jornadaRepo.countNormalShiftByEmpleadoIdAndWeek(idEmpleado, startOfWeek, endOfWeek);

        if (countNormalShifts >= 5) {
            throw new BusinessException("El empleado ingresado ya cuenta con 5 turnos normales esta semana.");
        }
    }

    static void validateLibreShiftsLimitPerWeek(
            JornadaLaboralRepository jornadaRepo,
            LocalDate fecha,
            Integer idEmpleado) {

        LocalDate startOfWeek = getWeekStart(fecha);
        LocalDate endOfWeek = getWeekEnd(fecha);
        int countLibreShifts = jornadaRepo.countLibreShiftByEmpleadoIdAndWeek(idEmpleado, startOfWeek, endOfWeek);

        if (countLibreShifts >= 2) {
            throw new BusinessException("El empleado no cuenta con más días libres esta semana.");
        }
    }

    private static void validateLibreShiftsLimitPerMonth(
            JornadaLaboralRepository jornadaRepo ,
            LocalDate fecha,
            Integer idEmpleado) {

        YearMonth yearMonth = YearMonth.of(fecha.getYear(), fecha.getMonth());
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();

        int countLibreShifts = jornadaRepo.countLibreShiftByEmpleadoIdAndMonth(idEmpleado, startOfMonth, endOfMonth);

        if (countLibreShifts >= 5) {
            throw new BusinessException("El empleado no cuenta con más días libres este mes.");
        }
    }

    private static void validateConceptLimitPerDay(
            JornadaLaboralRepository jornadaRepo,
            String conceptoNombre,
            LocalDate fecha) {

        int count = jornadaRepo.countEmpleadosByConceptoNombreAndFecha(conceptoNombre, fecha);

        if (count >= 2) {
            throw new BusinessException("Ya existen 2 empleados registrados para este concepto en la fecha ingresada.");
        }
    }

    private static void validateNoDuplicateConceptPerDay(
            JornadaLaboralRepository jornadaRepo,
            Integer idEmpleado,
            Integer idConcepto,
            LocalDate fecha) {

        if (jornadaRepo.existsByEmpleadoIdAndConceptoLaboralIdAndFecha(idEmpleado, idConcepto, fecha)) {
            throw new BusinessException("El empleado ya tiene registrado una jornada con este concepto en la fecha ingresada.");
        }
    }
}
