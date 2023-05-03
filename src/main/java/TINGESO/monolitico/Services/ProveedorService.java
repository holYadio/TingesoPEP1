package TINGESO.monolitico.Services;

import TINGESO.monolitico.Repositories.ProveedorRepository;
import TINGESO.monolitico.Entities.ProveedorEntity;
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
