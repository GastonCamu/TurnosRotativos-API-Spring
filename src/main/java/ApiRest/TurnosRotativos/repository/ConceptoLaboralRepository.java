package ApiRest.TurnosRotativos.repository;

import ApiRest.TurnosRotativos.entity.ConceptoLaboral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConceptoLaboralRepository extends JpaRepository<ConceptoLaboral, Integer> {

    List<ConceptoLaboral> findByNombreContainingIgnoreCase(String nombre);

    List<ConceptoLaboral> findByIdAndNombreContainingIgnoreCase(Integer id, String nombre);
}
