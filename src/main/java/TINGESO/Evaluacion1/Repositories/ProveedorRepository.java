package TINGESO.Evaluacion1.Repositories;

import TINGESO.Evaluacion1.Entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, Long> {
    @Query("SELECT p FROM ProveedorEntity p WHERE p.codigo = :codigo")
    ProveedorEntity findByCodigo(@Param("codigo")String codigo);
}
