package TINGESO.monolitico.Repositories;


import TINGESO.monolitico.Entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, Long> {
    ProveedorEntity findByCodigo(String codigo);
    @Query("SELECT p.nombre FROM ProveedorEntity p WHERE p.codigo = :codigo")
    String getNombreProveedor(@Param("codigo")String codigo);
}