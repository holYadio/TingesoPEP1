package tingeso.monolitico.services;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tingeso.monolitico.entities.DatosLaboratorioEntity;
import tingeso.monolitico.repositories.DatosLaboratorioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class DatosLaboratorioServiceTest {
    @Autowired
    private DatosLaboratorioService service;

    @MockBean
    private DatosLaboratorioRepository repository;

    @Test
    void testObtenerDatosLaboratorio() {
        DatosLaboratorioEntity datosLaboratorio1 = new DatosLaboratorioEntity();
        DatosLaboratorioEntity datosLaboratorio2 = new DatosLaboratorioEntity();
        List<DatosLaboratorioEntity> datosLaboratorioArrayList = new ArrayList<>();
        datosLaboratorioArrayList.add(datosLaboratorio1);
        datosLaboratorioArrayList.add(datosLaboratorio2);
        when(repository.findAll()).thenReturn(datosLaboratorioArrayList);

        assertEquals(datosLaboratorioArrayList, service.obtenerDatosLaboratorio());
    }

    @Test
    void testObtenerDatosLaboratorioPorProveedor() {
        DatosLaboratorioEntity datosLaboratorio1 = new DatosLaboratorioEntity();
        DatosLaboratorioEntity datosLaboratorio2 = new DatosLaboratorioEntity();
        List<DatosLaboratorioEntity> datosLaboratorioArrayList = new ArrayList<>();
        datosLaboratorioArrayList.add(datosLaboratorio1);
        datosLaboratorioArrayList.add(datosLaboratorio2);
        String proveedor = "proveedor1";
        when(repository.findByProveedor(proveedor)).thenReturn(datosLaboratorioArrayList);

        assertEquals(datosLaboratorioArrayList, service.obtenerDatosLaboratorioPorProveedor(proveedor));
    }

    @Test
    void testObtenerDatosLaboratorioPorProveedorYQuincena() {
        DatosLaboratorioEntity datosLaboratorio1 = new DatosLaboratorioEntity();
        String proveedor = "01003";
        String quincena = "2022/01/Q1";
        when(repository.findByProveedorAndQuincena(proveedor, quincena)).thenReturn(datosLaboratorio1);

        assertEquals(datosLaboratorio1, service.obtenerDatosLaboratorioPorProveedorYQuincena(proveedor, quincena));
    }

    @Test
    void testGuardarDatoDB() {
        String proveedor = "01003";
        String porcentaje_grasa = "10";
        String porcentaje_solido_total = "20";
        String quincena = "2022/01/Q1";
        DatosLaboratorioEntity expectedDato = new DatosLaboratorioEntity();
        expectedDato.setId(null);
        expectedDato.setProveedor(proveedor);
        expectedDato.setPorcentajeGrasa(porcentaje_grasa);
        expectedDato.setPorcentajeSolidoTotal(porcentaje_solido_total);
        expectedDato.setQuincena(quincena);

        service.guardarDatoDB(proveedor, porcentaje_grasa, porcentaje_solido_total, quincena);

        verify(repository).save(expectedDato);
    }

    @ParameterizedTest
    @CsvSource({
            "10, 0",
            "0, 0",
    })
    void testGetVariacionSolidoTotal(String solidos, double resultadoEsperado) {
        service.guardarDatoDB("01003", "10", solidos, "2022/01/Q1");
        double resultadoActual = service.getVariacionSolidoTotal("2022/01/Q2", "01003", solidos);
        assertEquals(resultadoEsperado, resultadoActual);
    }



    @Test
    void testGetVariacionGrasa() {
        double resultadoEsperado = 0.0;
        double resultadoActual = service.getVariacionGrasa("2022/01/Q2", "01003", "50");
        assertEquals(resultadoEsperado, resultadoActual);
    }

    @ParameterizedTest
    @CsvSource({
            "2022/01/Q2, 2022/01/Q1",
            "2022/01/Q1, 2021/12/Q2",
            "2022/03/Q1, 2022/02/Q2",
            "2022/10/Q1, 2022/09/Q2",
            "2022/11/Q2, 2022/11/Q1",
            "2022/12/Q3,  '' ",
    })
    void testQuincenaAnterior(String quincena, String resultadoEsperado) {
        String resultadoActual = service.quincenaAnterior(quincena);
        assertEquals(resultadoEsperado, resultadoActual);
    }
}