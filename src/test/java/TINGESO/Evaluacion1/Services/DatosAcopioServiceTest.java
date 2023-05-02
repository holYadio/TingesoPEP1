package TINGESO.Evaluacion1.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.mockito.stubbing.OngoingStubbing;
import org.slf4j.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import TINGESO.Evaluacion1.Entities.DatosAcopioEntity;
import TINGESO.Evaluacion1.Repositories.DatosAcopioRepository;
import TINGESO.Evaluacion1.Services.DatosLaboratorioService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class DatosAcopioServiceTest {
    @Mock
    DatosAcopioRepository repository;

    @Mock
    DatosLaboratorioService datosLaboratorioService;

    DatosAcopioService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new DatosAcopioService();
        service.datosAcopioRepository = repository;
        service.datosLaboratorioService = datosLaboratorioService;
    }


    @Test
    void testObtenerDatosAcopioPorProveedor() {
        ArrayList<DatosAcopioEntity> expected = new ArrayList<>();
        expected.add(new DatosAcopioEntity(1,"2023/01/01", "M", "01003", "10"));
        expected.add(new DatosAcopioEntity(2,"2023/01/01", "M", "01003", "10"));
        when(repository.findByProveedor("01003")).thenReturn(expected);
        ArrayList<DatosAcopioEntity> result = service.obtenerDatosAcopioPorProveedor("01003");
        assertEquals(expected, result);
    }

    @Test
    void testObtenerDatosAcopioPorQuincenayProveedor() {
        ArrayList<DatosAcopioEntity> datosAcopio = new ArrayList<>();
        datosAcopio.add(new DatosAcopioEntity(1,"2023/01/01", "M", "01003", "10"));
        datosAcopio.add(new DatosAcopioEntity(2,"2023/02/01", "M", "01003", "20"));
        datosAcopio.add(new DatosAcopioEntity(3,"2023/02/15", "M", "01003", "30"));
        datosAcopio.add(new DatosAcopioEntity(4,"2023/02/16", "M", "01003", "40"));
        ArrayList<DatosAcopioEntity> expected = new ArrayList<>();
        expected.add(new DatosAcopioEntity(2,"2023/02/01", "M", "01003", "20"));
        expected.add(new DatosAcopioEntity(3,"2023/02/15", "M", "01003", "30"));
        when(repository.findAll()).thenReturn(datosAcopio);
        ArrayList<DatosAcopioEntity> result = service.obtenerDatosAcopioPorQuincenayProveedor("2023/02/Q1", "01003");
        assertEquals(expected, result);
    }

    @Test
    void testGuardarDatoProveedor() {
        String fecha = "2022/01/01";
        String turno = "M";
        String proveedor = "01003";
        String kls_leche = "10";

        DatosAcopioEntity expectedDato = new DatosAcopioEntity();
        expectedDato.setId(null);
        expectedDato.setFecha(fecha);
        expectedDato.setTurno(turno);
        expectedDato.setProveedor(proveedor);
        expectedDato.setKls_leche(kls_leche);

        service.guardarDatoProveedorDB(fecha, turno, proveedor, kls_leche);

        verify(repository).save(expectedDato);
    }

    @Test
    void testKlsTotalLeche() {
        ArrayList<DatosAcopioEntity> datosAcopio = new ArrayList<>();
        DatosAcopioEntity datos1 = new DatosAcopioEntity();
        DatosAcopioEntity datos2 = new DatosAcopioEntity();
        datos1.setKls_leche("20");
        datos2.setKls_leche("30");
        datosAcopio.add(datos1);
        datosAcopio.add(datos2);
        double klsTotalLeche = service.KlsTotalLeche(datosAcopio);
        assertEquals(50, klsTotalLeche, 0);
    }


    @Test
    void testDiasEnvioLeche() {
        DatosAcopioEntity dato1 = new DatosAcopioEntity();
        dato1.setFecha("2023/04/20");
        dato1.setTurno("M");
        dato1.setProveedor("01003");
        dato1.setKls_leche("100");

        DatosAcopioEntity dato2 = new DatosAcopioEntity();
        dato2.setFecha("2023/04/20");
        dato2.setTurno("T");
        dato2.setProveedor("01003");
        dato2.setKls_leche("50");

        DatosAcopioEntity dato3 = new DatosAcopioEntity();
        dato3.setFecha("2023/04/21");
        dato3.setTurno("M");
        dato3.setProveedor("01003");
        dato3.setKls_leche("75");

        ArrayList<DatosAcopioEntity> datosAcopio = new ArrayList<>();
        datosAcopio.add(dato1);
        datosAcopio.add(dato2);
        datosAcopio.add(dato3);

        double result = service.diasEnvioLeche(datosAcopio);
        assertEquals(2, result);
    }

    @Test
    void testGetVariacion_leche() {
        String quincena = "2022/04/Q1";
        String codigoProveedor = "01003";
        double klsTotalLeche = 500.0;
        double expectedVariacion = 0;
        double actualVariacion = service.getVariacion_leche(quincena, codigoProveedor, klsTotalLeche);
        System.out.println(actualVariacion);
        Assertions.assertEquals(expectedVariacion, actualVariacion);
    }
}