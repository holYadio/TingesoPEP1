package TINGESO.Evaluacion1.Services;

import TINGESO.Evaluacion1.Repositories.ProveedorRepository;
import TINGESO.Evaluacion1.Entities.ProveedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    public ArrayList<ProveedorEntity> obtenerProveedores() {
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
    }

    public String obtenerNombreProveedor(String codigo){
        ProveedorEntity proveedor = proveedorRepository.findByCodigo(codigo);
        String nombreProveedor = proveedor.getNombre();
        return nombreProveedor;
    }

    public ProveedorEntity obtenerProveedorPorId(String codigo){
        return proveedorRepository.findByCodigo(codigo);
    }

    public ProveedorEntity guardarProveedor(String codigo, String nombre, String categoria, String retencion) {
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(codigo);
        proveedor.setNombre(nombre);
        proveedor.setCategoria(categoria);
        proveedor.setRetencion(retencion);
        return proveedorRepository.save(proveedor);
    }
}
