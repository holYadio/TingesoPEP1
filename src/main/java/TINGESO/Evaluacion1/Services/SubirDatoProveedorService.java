package TINGESO.Evaluacion1.Services;

import TINGESO.Evaluacion1.Repositories.SubirDatoProveedorRepository;
import TINGESO.Evaluacion1.Entities.SubirDatoProveedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class SubirDatoProveedorService {
    @Autowired
    SubirDatoProveedorRepository subirDatoProveedorRepository;

    public ArrayList<SubirDatoProveedorEntity> obtenerSubirDatoProveedores() {
        return (ArrayList<SubirDatoProveedorEntity>) subirDatoProveedorRepository.findAll();
    }

    public SubirDatoProveedorEntity obtenerSubirDatoProveedorPorProveedor(String proveedor) {
        return subirDatoProveedorRepository.findByProveedor(proveedor);
    }
}
