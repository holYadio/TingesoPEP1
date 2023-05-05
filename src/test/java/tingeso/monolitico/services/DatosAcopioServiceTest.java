package tingeso.monolitico.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tingeso.monolitico.entities.DatosAcopioEntity;
import tingeso.monolitico.repositories.DatosAcopioRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class DatosAcopioServiceTest {
    @Autowired
    DatosLaboratorioService datosLaboratorioService;

    @Autowired
    DatosAcopioService service;

    @MockBean
    DatosAcopioRepository repository;

    @Test
    void obtenerDatosAcopio() {
        DatosAcopioEntity dato1 = new DatosAcopioEntity();
        dato1.setId(1);
        dato1.setFecha("2022/04/01");
        dato1.setTurno("M");
        dato1.setProveedor("Proveedor 1");
        dato1.setKlsLeche("100");

        DatosAcopioEntity dato2 = new DatosAcopioEntity();
        dato2.setId(2);
        dato2.setFecha("2022/04/01");
        dato2.setTurno("T");
        dato2.setProveedor("Proveedor 2");
        dato2.setKlsLeche("200");

        List<DatosAcopioEntity> listaDatosAcopio = new ArrayList<>();
        listaDatosAcopio.add(dato1);
        listaDatosAcopio.add(dato2);

        when(repository.findAll()).thenReturn(listaDatosAcopio);

        List<DatosAcopioEntity> resultado = service.obtenerDatosAcopio();
        assertEquals(listaDatosAcopio.size(), resultado.size());
        assertEquals(listaDatosAcopio.get(0), resultado.get(0));
        assertEquals(listaDatosAcopio.get(1), resultado.get(1));
    }

    @Test
    void obtenerDatosAcopioPorProveedor() {
        List<DatosAcopioEntity> expected = new ArrayList<>();
        expected.add(new DatosAcopioEntity(1,"2023/01/01", "M", "01003", "10"));
        expected.add(new DatosAcopioEntity(2,"2023/01/01", "M", "01003", "10"));
        when(repository.findByProveedor("01003")).thenReturn(expected);
        List<DatosAcopioEntity> result = service.obtenerDatosAcopioPorProveedor("01003");
        assertEquals(expected, result);
    }

    @Test
    void obtenerDatosAcopioPorQuincenaYProveedorCasoA() {
        ArrayList<DatosAcopioEntity> datosAcopio = new ArrayList<>();
        datosAcopio.add(new DatosAcopioEntity(1,"2023/01/01", "M", "01003", "10"));
        datosAcopio.add(new DatosAcopioEntity(2,"2023/02/01", "M", "01003", "20"));
        datosAcopio.add(new DatosAcopioEntity(3,"2023/02/15", "M", "01003", "30"));
        datosAcopio.add(new DatosAcopioEntity(4,"2023/02/16", "M", "01003", "40"));
        ArrayList<DatosAcopioEntity> expected = new ArrayList<>();
        expected.add(new DatosAcopioEntity(2,"2023/02/01", "M", "01003", "20"));
        expected.add(new DatosAcopioEntity(3,"2023/02/15", "M", "01003", "30"));
        when(repository.findAll()).thenReturn(datosAcopio);
        List<DatosAcopioEntity> result = service.obtenerDatosAcopioPorQuincenayProveedor("2023/02/Q1", "01003");
        assertEquals(expected, result);
    }

    @Test
    void obtenerDatosAcopioPorQuincenaYProveedorCasoB() {
        ArrayList<DatosAcopioEntity> datosAcopio = new ArrayList<>();
        datosAcopio.add(new DatosAcopioEntity(1,"2023/01/01", "M", "01003", "10"));
        datosAcopio.add(new DatosAcopioEntity(2,"2023/02/01", "M", "01003", "20"));
        datosAcopio.add(new DatosAcopioEntity(3,"2023/02/15", "M", "01003", "30"));
        datosAcopio.add(new DatosAcopioEntity(4,"2023/03/16", "M", "01003", "40"));
        datosAcopio.add(new DatosAcopioEntity(5,"2023/03/28", "M", "01003", "50"));
        datosAcopio.add(new DatosAcopioEntity(6,"2023/03/31", "M", "01003", "60"));
        ArrayList<DatosAcopioEntity> expected = new ArrayList<>();
        expected.add(new DatosAcopioEntity(4,"2023/03/16", "M", "01003", "40"));
        expected.add(new DatosAcopioEntity(5,"2023/03/28", "M", "01003", "50"));
        expected.add(new DatosAcopioEntity(6,"2023/03/31", "M", "01003", "60"));
        when(repository.findAll()).thenReturn(datosAcopio);
        List<DatosAcopioEntity> result = service.obtenerDatosAcopioPorQuincenayProveedor("2023/03/Q2", "01003");
        assertEquals(expected, result);
    }

    @Test
    void guardarDatoProveedorDB() {
        String fecha = "2022/01/01";
        String turno = "M";
        String proveedor = "01003";
        String kls_leche = "10";

        DatosAcopioEntity expectedDato = new DatosAcopioEntity();
        expectedDato.setId(null);
        expectedDato.setFecha(fecha);
        expectedDato.setTurno(turno);
        expectedDato.setProveedor(proveedor);
        expectedDato.setKlsLeche(kls_leche);

        service.guardarDatoProveedorDB(fecha, turno, proveedor, kls_leche);

        verify(repository).save(expectedDato);
    }

    @Test
    void klsTotalLeche() {
        List<DatosAcopioEntity> datosAcopio = new ArrayList<>();
        DatosAcopioEntity datos1 = new DatosAcopioEntity();
        DatosAcopioEntity datos2 = new DatosAcopioEntity();
        datos1.setKlsLeche("20");
        datos2.setKlsLeche("30");
        datosAcopio.add(datos1);
        datosAcopio.add(datos2);
        double klsTotalLeche = service.klsTotalLeche(datosAcopio);
        assertEquals(50, klsTotalLeche, 0);
    }

    @Test
    void diasEnvioLeche() {
        DatosAcopioEntity dato1 = new DatosAcopioEntity();
        dato1.setFecha("2023/04/20");
        dato1.setTurno("M");
        dato1.setProveedor("01003");
        dato1.setKlsLeche("100");

        DatosAcopioEntity dato2 = new DatosAcopioEntity();
        dato2.setFecha("2023/04/20");
        dato2.setTurno("T");
        dato2.setProveedor("01003");
        dato2.setKlsLeche("50");

        DatosAcopioEntity dato3 = new DatosAcopioEntity();
        dato3.setFecha("2023/04/21");
        dato3.setTurno("M");
        dato3.setProveedor("01003");
        dato3.setKlsLeche("75");

        ArrayList<DatosAcopioEntity> datosAcopio = new ArrayList<>();
        datosAcopio.add(dato1);
        datosAcopio.add(dato2);
        datosAcopio.add(dato3);

        double result = service.diasEnvioLeche(datosAcopio);
        assertEquals(2, result);
    }

    @Test
    void getVariacionLeche() {
        String quincena = "2022/04/Q1";
        String codigoProveedor = "01003";
        double klsTotalLeche = 500.0;
        double expectedVariacion = 0;
        double actualVariacion = service.getVariacionLeche(quincena, codigoProveedor, klsTotalLeche);
        System.out.println(actualVariacion);
        Assertions.assertEquals(expectedVariacion, actualVariacion);
    }
}