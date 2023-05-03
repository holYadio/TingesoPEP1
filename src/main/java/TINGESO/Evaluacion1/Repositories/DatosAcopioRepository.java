package TINGESO.Evaluacion1.Repositories;

import TINGESO.Evaluacion1.Entities.DatosAcopioEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


@Repository
public interface DatosAcopioRepository extends JpaRepository<DatosAcopioEntity, Long>{
    List<DatosAcopioEntity> findByProveedor(String proveedor);
}
