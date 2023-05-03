package tingeso.monolitico.repositories;

import tingeso.monolitico.entities.PagoEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Long>{
}
