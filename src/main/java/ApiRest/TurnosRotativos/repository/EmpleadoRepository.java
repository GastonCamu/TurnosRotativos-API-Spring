package ApiRest.TurnosRotativos.repository;

import ApiRest.TurnosRotativos.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    boolean existsByNroDocumento(int nroDocumento);
    boolean existsByEmail(String email);
}
