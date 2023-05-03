package tingeso.monolitico.repositories;

import tingeso.monolitico.entities.DatosLaboratorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface DatosLaboratorioRepository extends JpaRepository<DatosLaboratorioEntity, Long>{

    List<DatosLaboratorioEntity> findByProveedor(String proveedor);


    DatosLaboratorioEntity findByProveedorAndQuincena(String proveedor,
                                                      String quincena);
}
