package TINGESO.Evaluacion1.Repositories;

import TINGESO.Evaluacion1.Entities.DatosAcopioEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;


@Repository
public interface DatosAcopioRepository extends JpaRepository<DatosAcopioEntity, Long>{
    ArrayList<DatosAcopioEntity> findByProveedor(String proveedor);
}
