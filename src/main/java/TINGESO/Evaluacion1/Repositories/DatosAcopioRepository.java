package TINGESO.Evaluacion1.Repositories;

import TINGESO.Evaluacion1.Entities.DatosAcopioEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;


@Repository
public interface DatosAcopioRepository extends JpaRepository<DatosAcopioEntity, Long>{
    @Query("SELECT * FROM DatosAcopioEntity a WHERE a.proveedor = :proveedor")
    ArrayList<DatosAcopioEntity> findByProveedor(@Param("proveedor") String proveedor);
}
