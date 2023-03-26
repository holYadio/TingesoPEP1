package TINGESO.Evaluacion1.Repositories;
import TINGESO.Evaluacion1.Entities.AcopioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AcopioRepository extends JpaRepository<AcopioEntity, Long>{
    @Query("SELECT a FROM AcopioEntity a WHERE a.proveedor = :proveedor")
    AcopioEntity findByProveedor(@Param("proveedor")String proveedor);
}
