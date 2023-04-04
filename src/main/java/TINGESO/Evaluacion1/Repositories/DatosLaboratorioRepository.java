package TINGESO.Evaluacion1.Repositories;

import TINGESO.Evaluacion1.Entities.DatosLaboratorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

@Repository
public interface DatosLaboratorioRepository extends JpaRepository<DatosLaboratorioEntity, Long>{
    @Query("SELECT * FROM DatosLaboratorioEntity p WHERE p.proveedor = :proveedor")
    ArrayList<DatosLaboratorioEntity> findByProveedor(@Param("proveedor")String proveedor);

    @Query("SELECT * FROM DatosLaboratorioEntity p WHERE p.proveedor = :proveedor AND p.quincena = :quincena")
    DatosLaboratorioEntity findByProveedorAndQuincena(String proveedor, String quincena);
}
