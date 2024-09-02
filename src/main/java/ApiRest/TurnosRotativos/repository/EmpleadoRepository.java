package ApiRest.TurnosRotativos.repository;

import ApiRest.TurnosRotativos.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByNroDocumento(int nroDocumento);

    Optional<Empleado> findByEmail(String email);
    boolean existsByNroDocumento(int nroDocumento);
    boolean existsByEmail(String email);
}
