package ApiRest.TurnosRotativos.repository;

import ApiRest.TurnosRotativos.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
