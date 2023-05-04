package tingeso.monolitico.services;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tingeso.monolitico.entities.DatosAcopioEntity;
import tingeso.monolitico.entities.PagoEntity;
import tingeso.monolitico.entities.ProveedorEntity;
import tingeso.monolitico.repositories.PagoRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@SpringBootTest
class PlanillaDePagosTest {
    @Mock
    PagoRepository pagoRepository;

    @Mock
    DatosAcopioService datosAcopioService;

    @Mock
    DatosLaboratorioService datosLaboratorioService;

    @Mock
    ProveedorService proveedorService;

    @InjectMocks
    PlanillaDePagos planillaDePagos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerPagos() {
        ArrayList<PagoEntity> pagos = new ArrayList<>();
        pagos.add(new PagoEntity(1,"2023/03/Q1", "01003", "Alcides", "150", "1", "10", "10", "1", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "150.0", "0.0", "0.0", "0.0", "0.0","0.0","0"));
        pagos.add(new PagoEntity(1,"2023/03/Q2", "01003", "Alcides", "150", "1", "10", "10", "1", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "150.0", "0.0", "0.0", "0.0", "0.0","0.0","0"));

        Mockito.when(pagoRepository.findAll()).thenReturn(pagos);
        List<PagoEntity> resultado = planillaDePagos.obtenerPagos();

        Assertions.assertEquals(pagos, resultado);
    }

    @Test
    void testDeleteAllTest() {
        planillaDePagos.deleteAll();

        verify(pagoRepository, Mockito.times(1)).deleteAll();
    }

    // Test para calcular el pago por categoria
    @ParameterizedTest
    @CsvSource({
            "A, 700",
            "B, 550",
            "C, 400",
            "D, 250",
            "E, 0",
    })
    void testCalcularPagoPorCategoria(String categoria, double pago) {
        ProveedorEntity proveedor = new ProveedorEntity("01003", "Juan", categoria, "si");
        double resultado = planillaDePagos.calcularPagoPorCategoria(proveedor, 100);
        Assertions.assertEquals(pago * 100, resultado);
    }

    // Test para calcular el pago por grasas
    @ParameterizedTest
    @CsvSource({
            "-1, 0",
            "10, 3000",
            "22, 8000",
            "60, 12000",
    })
    void testCalcularPagoPorGrasa(String grasa, double pago) {
        double resultadoObtenido = planillaDePagos.calcularPagoPorGrasas(grasa, 100.0);
        Assertions.assertEquals(pago, resultadoObtenido);
    }

    // Test para calcular el pago por solidos totales
    @ParameterizedTest
    @CsvSource({
            "-1, 0",
            "5, -13000",
            "15, -9000",
            "30, 9500",
            "60, 15000",
    })
    void testCalcularPagoPorSolidosTotales(String solidosTotales, double pago) {
        double resultadoObtenido = planillaDePagos.calcularPagoPorSolidosTotales(solidosTotales, 100.0);
        Assertions.assertEquals(pago, resultadoObtenido);
    }

    @Test
    void testCalcularBonificacionPorFrecuenciaCasoA() {
        ArrayList<DatosAcopioEntity> datosAcopioEntity = new ArrayList<>();
        datosAcopioEntity.add(new DatosAcopioEntity(1,"2023/03/01","M", "0001", "100"));
        datosAcopioEntity.add(new DatosAcopioEntity(2,"2023/03/02","M", "0001", "200"));
        datosAcopioEntity.add(new DatosAcopioEntity(3,"2023/03/03","M", "0001", "150"));
        datosAcopioEntity.add(new DatosAcopioEntity(4,"2023/03/04","M", "0001", "250"));
        datosAcopioEntity.add(new DatosAcopioEntity(5,"2023/03/05","M", "0001", "175"));
        datosAcopioEntity.add(new DatosAcopioEntity(6,"2023/03/06","M", "0001", "300"));
        datosAcopioEntity.add(new DatosAcopioEntity(7,"2023/03/07","M", "0001", "125"));
        datosAcopioEntity.add(new DatosAcopioEntity(8,"2023/03/08","M", "0001", "175"));
        datosAcopioEntity.add(new DatosAcopioEntity(9,"2023/03/09","M", "0001", "100"));
        datosAcopioEntity.add(new DatosAcopioEntity(10,"2023/03/10","M", "0001", "150"));
        datosAcopioEntity.add(new DatosAcopioEntity(11,"2023/03/10","M", "0001", "100"));
        double resultadoEsperado = 120;
        double resultadoObtenido = planillaDePagos.calcularBonificacionPorFrecuencia(datosAcopioEntity, 1000.0);
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    void testCalcularBonificacionPorFrecuenciaCasoB() {
        ArrayList<DatosAcopioEntity> datosAcopioEntity = new ArrayList<>();
        datosAcopioEntity.add(new DatosAcopioEntity(1,"2023/03/01","T", "0001", "100"));
        datosAcopioEntity.add(new DatosAcopioEntity(2,"2023/03/02","T", "0001", "200"));
        datosAcopioEntity.add(new DatosAcopioEntity(3,"2023/03/03","T", "0001", "150"));
        datosAcopioEntity.add(new DatosAcopioEntity(4,"2023/03/04","T", "0001", "250"));
        datosAcopioEntity.add(new DatosAcopioEntity(5,"2023/03/05","T", "0001", "175"));
        datosAcopioEntity.add(new DatosAcopioEntity(6,"2023/03/06","T", "0001", "300"));
        datosAcopioEntity.add(new DatosAcopioEntity(7,"2023/03/07","T", "0001", "125"));
        datosAcopioEntity.add(new DatosAcopioEntity(8,"2023/03/08","T", "0001", "175"));
        datosAcopioEntity.add(new DatosAcopioEntity(9,"2023/03/09","T", "0001", "100"));
        datosAcopioEntity.add(new DatosAcopioEntity(10,"2023/03/10","T", "0001", "150"));
        datosAcopioEntity.add(new DatosAcopioEntity(11,"2023/03/10","T", "0001", "100"));
        double resultadoEsperado = 80;
        double resultadoObtenido = planillaDePagos.calcularBonificacionPorFrecuencia(datosAcopioEntity, 1000.0);
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    void testCalcularBonificacionPorFrecuenciaCasoC() {
        ArrayList<DatosAcopioEntity> datosAcopioEntity = new ArrayList<>();
        datosAcopioEntity.add(new DatosAcopioEntity(1,"2023/03/01","M", "0001", "100"));
        datosAcopioEntity.add(new DatosAcopioEntity(2,"2023/03/02","M", "0001", "200"));
        datosAcopioEntity.add(new DatosAcopioEntity(3,"2023/03/03","M", "0001", "150"));
        datosAcopioEntity.add(new DatosAcopioEntity(4,"2023/03/04","M", "0001", "250"));
        datosAcopioEntity.add(new DatosAcopioEntity(5,"2023/03/05","M", "0001", "175"));
        datosAcopioEntity.add(new DatosAcopioEntity(6,"2023/03/06","M", "0001", "300"));
        datosAcopioEntity.add(new DatosAcopioEntity(7,"2023/03/07","M", "0001", "125"));
        datosAcopioEntity.add(new DatosAcopioEntity(8,"2023/03/08","M", "0001", "175"));
        datosAcopioEntity.add(new DatosAcopioEntity(9,"2023/03/09","M", "0001", "100"));
        datosAcopioEntity.add(new DatosAcopioEntity(10,"2023/03/10","M", "0001", "150"));
        datosAcopioEntity.add(new DatosAcopioEntity(11,"2023/03/10","M", "0001", "100"));
        datosAcopioEntity.add(new DatosAcopioEntity(12,"2023/03/01","T", "0001", "100"));
        datosAcopioEntity.add(new DatosAcopioEntity(13,"2023/03/02","T", "0001", "200"));
        datosAcopioEntity.add(new DatosAcopioEntity(14,"2023/03/03","T", "0001", "150"));
        datosAcopioEntity.add(new DatosAcopioEntity(15,"2023/03/04","T", "0001", "250"));
        datosAcopioEntity.add(new DatosAcopioEntity(16,"2023/03/05","T", "0001", "175"));
        datosAcopioEntity.add(new DatosAcopioEntity(17,"2023/03/06","T", "0001", "300"));
        datosAcopioEntity.add(new DatosAcopioEntity(18,"2023/03/07","T", "0001", "125"));
        datosAcopioEntity.add(new DatosAcopioEntity(19,"2023/03/08","T", "0001", "175"));
        datosAcopioEntity.add(new DatosAcopioEntity(20,"2023/03/09","T", "0001", "100"));
        datosAcopioEntity.add(new DatosAcopioEntity(21,"2023/03/10","T", "0001", "150"));
        datosAcopioEntity.add(new DatosAcopioEntity(22,"2023/03/10","T", "0001", "100"));
        double resultadoEsperado = 200;
        double resultadoObtenido = planillaDePagos.calcularBonificacionPorFrecuencia(datosAcopioEntity, 1000.0);
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    void testCalcularBonificacionPorFrecuenciaCasoD() {
        ArrayList<DatosAcopioEntity> datosAcopioEntity = new ArrayList<>();
        datosAcopioEntity.add(new DatosAcopioEntity(1,"2023/03/01","A", "0001", "100"));
        datosAcopioEntity.add(new DatosAcopioEntity(2,"2023/03/02","A", "0001", "200"));
        datosAcopioEntity.add(new DatosAcopioEntity(3,"2023/03/03","A", "0001", "150"));
        datosAcopioEntity.add(new DatosAcopioEntity(4,"2023/03/04","A", "0001", "250"));
        datosAcopioEntity.add(new DatosAcopioEntity(5,"2023/03/05","A", "0001", "175"));
        datosAcopioEntity.add(new DatosAcopioEntity(6,"2023/03/06","A", "0001", "300"));
        datosAcopioEntity.add(new DatosAcopioEntity(7,"2023/03/07","A", "0001", "125"));
        datosAcopioEntity.add(new DatosAcopioEntity(8,"2023/03/08","A", "0001", "175"));
        datosAcopioEntity.add(new DatosAcopioEntity(9,"2023/03/09","A", "0001", "100"));
        datosAcopioEntity.add(new DatosAcopioEntity(10,"2023/03/10","A", "0001", "150"));
        datosAcopioEntity.add(new DatosAcopioEntity(11,"2023/03/10","A", "0001", "100"));
        double resultadoEsperado = 0;
        double resultadoObtenido = planillaDePagos.calcularBonificacionPorFrecuencia(datosAcopioEntity, 1000.0);
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido);
    }


    @ParameterizedTest
    @CsvSource({
            "-1, 0",
            "5, 0",
            "20, 70",
            "30, 150",
            "60, 300",
    })
    void testCalcularDescuentoPorVariacionLeche(int porcentajeVariacionLeche, double esperado) {
        double descuento = planillaDePagos.calcularDescuentoPorVariacionLeche(porcentajeVariacionLeche, 1000);
        Assertions.assertEquals(esperado, descuento);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 0",
            "5, 0",
            "20, 120",
            "30, 200",
            "60, 300",
    })
    void testCalcularDescuentoPorVariacionGrasa(int porcentajeVariacionGrasa, double esperado) {
        double descuento = planillaDePagos.calcularDescuentoPorVariacionGrasa(porcentajeVariacionGrasa, 1000);
        Assertions.assertEquals(esperado, descuento);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 0",
            "5, 0",
            "10, 180",
            "30, 270",
            "60, 450",
    })
    void testCalcularDescuentoPorVariacionSolidosTotales(int porcentajeVariacionSolidoTotal, double esperado) {
        double pagoAcopioLeche = 1000;

        double descuento = planillaDePagos.calcularDescuentoPorVariacionSolidosTotales(porcentajeVariacionSolidoTotal, pagoAcopioLeche);

        Assertions.assertEquals(esperado, descuento);
    }

    @Test
    void testCalcularRetencionCasoA() {
        double salario = 1000000;
        double retencion = planillaDePagos.calcularRetencion(salario);
        Assertions.assertEquals(salario * PlanillaDePagos.IMPUESTORETENCION / 100, retencion);
    }

    @Test
    void testCalcularRetencionCasoB() {
        double salario = 500000;
        double retencion = planillaDePagos.calcularRetencion(salario);
        Assertions.assertEquals(0, retencion);
    }

    @Test
    void testGuardarPagoDB() {
        String quincena = "2023/03/Q1";
        String codigoProveedor = "01003";
        String nombreProveedor = "Juan Perez";
        String KlsTotalLeche = "1000";
        String diasEnvioLeche = "10";
        String PromedioKilosLecheDiario = "100";
        String PorcentajeFrecuenciaDiariaEnvioLeche = "100";
        String PorcentajeGrasa = "30";
        String PorcentajeVariacionGrasa = "30";
        String PorcentajeSolidoTotal = "25";
        String PorcentajeVariacionSolidoTotal = "20";
        String PagoPorLeche = "1000";
        String PagoPorGrasa = "300";
        String PagoPorSolidosTotales = "250";
        String BonificacionPorFrecuencia = "768";
        String DctoVariacionLeche = "0";
        String DctoVariacionGrasa = "400";
        String DctoVariacionST = "540";
        String PagoTOTAL = "2378";
        String MontoRetencion = "237.8";
        String MontoFINAL = "2140.2";
        PagoEntity expectedPago = new PagoEntity();
        expectedPago.setId(null);
        expectedPago.setQuincena(quincena);
        expectedPago.setCodigoProveedor(codigoProveedor);
        expectedPago.setNombreProveedor(nombreProveedor);
        expectedPago.setKlsTotalLeche(KlsTotalLeche);
        expectedPago.setDiasEnvioLeche(diasEnvioLeche);
        expectedPago.setPromedioKilosLecheDiario(PromedioKilosLecheDiario);
        expectedPago.setPorcentajeFrecuenciaDiariaEnvioLeche(PorcentajeFrecuenciaDiariaEnvioLeche);
        expectedPago.setPorcentajeGrasa(PorcentajeGrasa);
        expectedPago.setPorcentajeVariacionGrasa(PorcentajeVariacionGrasa);
        expectedPago.setPorcentajeSolidoTotal(PorcentajeSolidoTotal);
        expectedPago.setPorcentajeVariacionSolidoTotal(PorcentajeVariacionSolidoTotal);
        expectedPago.setPagoPorLeche(PagoPorLeche);
        expectedPago.setPagoPorGrasa(PagoPorGrasa);
        expectedPago.setPagoPorSolidosTotales(PagoPorSolidosTotales);
        expectedPago.setBonificacionPorFrecuencia(BonificacionPorFrecuencia);
        expectedPago.setDctoVariacionLeche(DctoVariacionLeche);
        expectedPago.setDctoVariacionGrasa(DctoVariacionGrasa);
        expectedPago.setDctoVariacionST(DctoVariacionST);
        expectedPago.setPagoTotal(PagoTOTAL);
        expectedPago.setMontoRetencion(MontoRetencion);
        expectedPago.setMontoFinal(MontoFINAL);
        planillaDePagos.guardarPagoDB(quincena, codigoProveedor, nombreProveedor, KlsTotalLeche, diasEnvioLeche, PromedioKilosLecheDiario, PorcentajeFrecuenciaDiariaEnvioLeche, PorcentajeGrasa, PorcentajeVariacionGrasa, PorcentajeSolidoTotal, PorcentajeVariacionSolidoTotal, PagoPorLeche, PagoPorGrasa, PagoPorSolidosTotales, BonificacionPorFrecuencia, DctoVariacionLeche, DctoVariacionGrasa, DctoVariacionST, PagoTOTAL, MontoRetencion, MontoFINAL);
        verify(pagoRepository).save(expectedPago);
    }
}