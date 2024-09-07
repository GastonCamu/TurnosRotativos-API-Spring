package ApiRest.TurnosRotativos.repository;

import ApiRest.TurnosRotativos.entity.ConceptoLaboral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConceptoLaboralRepository extends JpaRepository<ConceptoLaboral, Long> {

    List<ConceptoLaboral> findByNombreContainingIgnoreCase(String nombre);

    List<ConceptoLaboral> findByIdAndNombreContainingIgnoreCase(Long id, String nombre);

    @Query("SELECT c.nombre FROM ConceptoLaboral c WHERE c.id = :id")
    String findNombreById(@Param("id") Long id);
}
