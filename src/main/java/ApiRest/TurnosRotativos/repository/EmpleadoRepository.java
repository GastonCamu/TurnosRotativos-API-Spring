package ApiRest.TurnosRotativos.repository;

import ApiRest.TurnosRotativos.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByNroDocumento(Integer nroDocumento);

    Optional<Empleado> findByEmail(String email);

    boolean existsByNroDocumento(Integer nroDocumento);

    boolean existsByEmail(String email);
}
