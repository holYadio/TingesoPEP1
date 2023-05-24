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

    /**
     * Funcion para obtener todos los proveedores
     * @return
     */
    public List<ProveedorEntity> obtenerProveedores() {
        return  proveedorRepository.findAll();
    }

    /**
     * Funcion para obtener el nombre de un proveedor
     * @param codigo: String con el codigo del proveedor
     * @return
     */
    public String obtenerNombreProveedor(String codigo){
        ProveedorEntity proveedor = proveedorRepository.findByCodigo(codigo);
        return proveedor.getNombre();
    }

    /**
     * Funcion para obtener el nombre de un proveedor
     * @param codigo: String con el codigo del proveedor
     * @return
     */
    public ProveedorEntity obtenerProveedorPorId(String codigo){
        return proveedorRepository.findByCodigo(codigo);
    }

    /**
     * Funcion para guardar un proveedor
     * @param codigo
     * @param nombre
     * @param categoria
     * @param retencion
     */
    public void guardarProveedor(String codigo, String nombre, String categoria, String retencion) {
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(codigo);
        proveedor.setNombre(nombre);
        proveedor.setCategoria(categoria.toUpperCase());
        proveedor.setRetencion(retencion);
        proveedorRepository.save(proveedor);
    }
}
