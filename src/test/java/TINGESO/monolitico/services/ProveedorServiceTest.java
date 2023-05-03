package tingeso.monolitico.services;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import tingeso.monolitico.entities.ProveedorEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import tingeso.monolitico.repositories.ProveedorRepository;

@SpringBootTest
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @Test
    void testObtenerProveedores() {
        ArrayList<ProveedorEntity> proveedores = new ArrayList<>();
        proveedores.add(new ProveedorEntity());
        Mockito.when(proveedorRepository.findAll()).thenReturn(proveedores);
        ArrayList<ProveedorEntity> proveedoresObtenidos = proveedorService.obtenerProveedores();
        assertEquals(proveedores, proveedoresObtenidos);
    }

    @Test
    void testObtenerNombreProveedor() {
        String codigo = "01003";
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(codigo);
        proveedor.setNombre("Proveedor 1");
        Mockito.when(proveedorRepository.findByCodigo(codigo)).thenReturn(proveedor);
        String nombreObtenido = proveedorService.obtenerNombreProveedor(codigo);
        assertEquals(proveedor.getNombre(), nombreObtenido);
    }

    @Test
    void testObtenerProveedorPorId() {
        String codigo = "01003";
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(codigo);
        proveedor.setNombre("Proveedor 1");
        Mockito.when(proveedorRepository.findByCodigo(codigo)).thenReturn(proveedor);
        ProveedorEntity proveedorObtenido = proveedorService.obtenerProveedorPorId(codigo);
        assertEquals(proveedor, proveedorObtenido);
    }

    @Test
    void testGuardarProveedor() {
        String codigo = "01003";
        String nombre = "Alcides";
        String categoria = "A";
        String retencion = "si";
        proveedorService.guardarProveedor(codigo, nombre, categoria, retencion);
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(codigo);
        proveedor.setNombre(nombre);
        proveedor.setCategoria(categoria.toUpperCase());
        proveedor.setRetencion(retencion);
        Mockito.verify(proveedorRepository).save(proveedor);
    }
}