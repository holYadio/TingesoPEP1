package TINGESO.Evaluacion1.Repositories;

import TINGESO.Evaluacion1.Entities.SubirDatoProveedorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SubirDatoProveedorRepository extends JpaRepository<SubirDatoProveedorEntity, Long>{
    @Query("SELECT p FROM SubirDatoProveedorEntity p WHERE p.proveedor = :proveedor")
    SubirDatoProveedorEntity findByProveedor(String proveedor);

}
