package TINGESO.Evaluacion1.Repositories;

import TINGESO.Evaluacion1.Entities.SubirDatoLaboratorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface SubirDatoLaboratorioRepository extends JpaRepository<SubirDatoLaboratorioEntity, Long>{
    @Query("SELECT p FROM SubirDatoLaboratorioEntity p WHERE p.proveedor = :proveedor")
    SubirDatoLaboratorioEntity findByProveedor(@Param("proveedor")String proveedor);
}
