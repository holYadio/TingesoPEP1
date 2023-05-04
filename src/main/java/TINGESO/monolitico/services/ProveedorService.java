package tingeso.monolitico.services;

import tingeso.monolitico.repositories.ProveedorRepository;
import tingeso.monolitico.entities.ProveedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    public List<ProveedorEntity> obtenerProveedores() {
        return  proveedorRepository.findAll();
    }

    public String obtenerNombreProveedor(String codigo){
        ProveedorEntity proveedor = proveedorRepository.findByCodigo(codigo);
        return proveedor.getNombre();
    }

    public ProveedorEntity obtenerProveedorPorId(String codigo){
        return proveedorRepository.findByCodigo(codigo);
    }

    public void guardarProveedor(String codigo, String nombre, String categoria, String retencion) {
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(codigo);
        proveedor.setNombre(nombre);
        proveedor.setCategoria(categoria.toUpperCase());
        proveedor.setRetencion(retencion);
        proveedorRepository.save(proveedor);
    }
}
