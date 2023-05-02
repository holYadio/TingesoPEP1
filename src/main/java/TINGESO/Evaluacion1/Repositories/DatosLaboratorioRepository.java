package TINGESO.Evaluacion1.Repositories;

import TINGESO.Evaluacion1.Entities.DatosLaboratorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface DatosLaboratorioRepository extends JpaRepository<DatosLaboratorioEntity, Long>{

    ArrayList<DatosLaboratorioEntity> findByProveedor(String proveedor);


    DatosLaboratorioEntity findByProveedorAndQuincena(String proveedor,
                                                      String quincena);
}
