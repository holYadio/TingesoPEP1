package TINGESO.Evaluacion1.Services;

import TINGESO.Evaluacion1.Entities.DatosLaboratorioEntity;
import TINGESO.Evaluacion1.Repositories.DatosLaboratorioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatosLaboratorioServiceTest {

    @Autowired
    private DatosLaboratorioRepository datosLaboratorioRepository;

    @Autowired
    private DatosLaboratorioService datosLaboratorioService;

    @Test
    void obtenerDatosLaboratorio() {
        DatosLaboratorioEntity entity1 = new DatosLaboratorioEntity();
        entity1.setId(1);
        entity1.setProveedor("Proveedor1");
        entity1.setPorcentaje_grasa("10");
        entity1.setPorcentaje_solido_total("20");
        entity1.setQuincena("2023/01/Q1");

        DatosLaboratorioEntity entity2 = new DatosLaboratorioEntity();
        entity2.setId(2);
        entity2.setProveedor("Proveedor2");
        entity2.setPorcentaje_grasa("5");
        entity2.setPorcentaje_solido_total("30");
        entity2.setQuincena("2023/01/Q1");

        Mockito.when(datosLaboratorioRepository.findAll())
                .thenReturn(Arrays.asList(entity1, entity2));

        List<DatosLaboratorioEntity> result = datosLaboratorioService.obtenerDatosLaboratorio();

        assertEquals(2, result.size());
        assertEquals(entity1.getProveedor(), result.get(0).getProveedor());
        assertEquals(entity2.getPorcentaje_solido_total(), result.get(1).getPorcentaje_solido_total());
    }

    @Test
    void obtenerDatosLaboratorioPorProveedor() {
        DatosLaboratorioEntity entity1 = new DatosLaboratorioEntity();
        entity1.setId(1);
        entity1.setProveedor("Proveedor1");
        entity1.setPorcentaje_grasa("10");
        entity1.setPorcentaje_solido_total("20");
        entity1.setQuincena("2023/01/Q1");

        DatosLaboratorioEntity entity2 = new DatosLaboratorioEntity();
        entity2.setId(2);
        entity2.setProveedor("Proveedor1");
        entity2.setPorcentaje_grasa("5");
        entity2.setPorcentaje_solido_total("30");
        entity2.setQuincena("2023/02/Q1");

        Mockito.when(datosLaboratorioRepository.findByProveedor("Proveedor1"))
                .thenReturn((ArrayList<DatosLaboratorioEntity>) Arrays.asList(entity1, entity2));

        List<DatosLaboratorioEntity> result = datosLaboratorioService.obtenerDatosLaboratorioPorProveedor("Proveedor1");

        assertEquals(2, result.size());
        assertEquals(entity1.getQuincena(), result.get(0).getQuincena());
        assertEquals(entity2.getPorcentaje_grasa(), result.get(1).getPorcentaje_grasa());
    }

    @Test
    void obtenerDatosLaboratorioPorProveedorYQuincena() {
        DatosLaboratorioEntity entity1 = new DatosLaboratorioEntity();
        entity1.setId(1);
        entity1.setProveedor("Proveedor1");
        entity1.setPorcentaje_grasa("10");
        entity1.setPorcentaje_solido_total("20");
        entity1.setQuincena("2023/01/Q1");

        Mockito.when(datosLaboratorioRepository.findByProveedorAndQuincena("Proveedor1", "2023/01/Q1"))
                .thenReturn(entity1);

        DatosLaboratorioEntity result = datosLaboratorioService.obtenerDatosLaboratorioPorProveedorYQuincena("Proveedor1", "2023/01/Q1");

        assertEquals(entity1.getId(), result.getId());
        assertEquals(entity1.getPorcentaje_solido_total(), result.getPorcentaje_solido_total());
    }

    @Test
    void guardarDato() {
    }

    @Test
    void guardarDatoDB() {
    }
}