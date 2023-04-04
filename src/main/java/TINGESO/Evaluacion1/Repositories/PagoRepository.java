package TINGESO.Evaluacion1.Repositories;

import TINGESO.Evaluacion1.Entities.PagoEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Long>{
}
