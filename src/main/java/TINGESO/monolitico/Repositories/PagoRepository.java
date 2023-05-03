package TINGESO.monolitico.Repositories;

import TINGESO.monolitico.Entities.PagoEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Long>{
}
