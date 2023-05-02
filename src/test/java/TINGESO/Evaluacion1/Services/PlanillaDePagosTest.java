package TINGESO.Evaluacion1.Services;

import TINGESO.Evaluacion1.Entities.DatosAcopioEntity;
import TINGESO.Evaluacion1.Entities.DatosLaboratorioEntity;
import TINGESO.Evaluacion1.Entities.PagoEntity;
import TINGESO.Evaluacion1.Entities.ProveedorEntity;
import TINGESO.Evaluacion1.Repositories.PagoRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

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
    void obtenerPagosTest() {
        ArrayList<PagoEntity> pagos = new ArrayList<>();
        pagos.add(new PagoEntity(1,"2023/03/Q1", "01003", "Alcides", "150", "1", "10", "10", "1", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "150.0", "0.0", "0.0", "0.0", "0.0","0.0","0"));
        pagos.add(new PagoEntity(1,"2023/03/Q2", "01003", "Alcides", "150", "1", "10", "10", "1", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "150.0", "0.0", "0.0", "0.0", "0.0","0.0","0"));

        Mockito.when(pagoRepository.findAll()).thenReturn(pagos);
        ArrayList<PagoEntity> resultado = planillaDePagos.obtenerPagos();

        Assertions.assertEquals(pagos, resultado);
    }

    @Test
    void testDeleteAllTest() {
        planillaDePagos.deleteAll();

        verify(pagoRepository, Mockito.times(1)).deleteAll();
    }

    @Test
    void testCalcularPagoPorCategoriaTest() {
        ProveedorEntity proveedor = new ProveedorEntity("01003", "Juan", "A", "si");
        double kls_leche = 100.0;
        double resultado = planillaDePagos.calcularPagoPorCategoria(proveedor, kls_leche);

        Assertions.assertEquals(700 * kls_leche, resultado);
    }

    @Test
    void testCalcularPagoPorGrasas() {
        double resultadoEsperado = 3000.0;
        double resultadoObtenido = planillaDePagos.calcularPagoPorGrasas("10", 100.0);
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    void testCalcularPagoPorSolidosTotales() {
        double resultadoEsperado = -13000.0;
        double resultadoObtenido = planillaDePagos.calcularPagoPorSolidosTotales("5", 100.0);
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido);
    }


    @Test
    void testCalcularBonificacionPorFrecuencia() {
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
        double resultadoEsperado = 768.0;
        double resultadoObtenido = planillaDePagos.calcularBonificacionPorFrecuencia(datosAcopioEntity, 6400.0);
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido);
    }


    @Test
    void testCalcularDescuentoPorVariacionLeche() {
        String quincena = "2023/03/Q1";
        String codigoProveedor = "01003";
        double klsTotalLeche = 1000;
        double pagoAcopioLeche = 2000;

        double descuento = planillaDePagos.calcularDescuentoPorVariacionLeche(quincena, codigoProveedor, klsTotalLeche, pagoAcopioLeche);

        Assertions.assertEquals(0, descuento);
    }

    @Test
    void testCalcularDescuentoPorVariacionGrasa() {
        double porcentajeVariacionGrasa = 30;
        double pagoAcopioLeche = 2000;

        double descuento = planillaDePagos.calcularDescuentoPorVariacionGrasa(porcentajeVariacionGrasa, pagoAcopioLeche);

        Assertions.assertEquals(400, descuento);
    }

    @Test
    void testCalcularDescuentoPorVariacionSolidosTotales() {
        double porcentajeVariacionSolidoTotal = 20;
        double pagoAcopioLeche = 2000;

        double descuento = planillaDePagos.calcularDescuentoPorVariacionSolidosTotales(porcentajeVariacionSolidoTotal, pagoAcopioLeche);

        Assertions.assertEquals(540, descuento);
    }

    @Test
    void testCalcularRetencion() {
        double salario = 1000000;
        double retencion = planillaDePagos.calcularRetencion(salario);
        Assertions.assertEquals(salario * PlanillaDePagos.IMPUESTORETENCION / 100, retencion);
    }

    @Test
    void testCalcularPagoFinal() {
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
        expectedPago.setPagoTOTAL(PagoTOTAL);
        expectedPago.setMontoRetencion(MontoRetencion);
        expectedPago.setMontoFINAL(MontoFINAL);
        planillaDePagos.guardarPagoDB(quincena, codigoProveedor, nombreProveedor, KlsTotalLeche, diasEnvioLeche, PromedioKilosLecheDiario, PorcentajeFrecuenciaDiariaEnvioLeche, PorcentajeGrasa, PorcentajeVariacionGrasa, PorcentajeSolidoTotal, PorcentajeVariacionSolidoTotal, PagoPorLeche, PagoPorGrasa, PagoPorSolidosTotales, BonificacionPorFrecuencia, DctoVariacionLeche, DctoVariacionGrasa, DctoVariacionST, PagoTOTAL, MontoRetencion, MontoFINAL);
        verify(pagoRepository).save(expectedPago);
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
        expectedPago.setPagoTOTAL(PagoTOTAL);
        expectedPago.setMontoRetencion(MontoRetencion);
        expectedPago.setMontoFINAL(MontoFINAL);
        planillaDePagos.guardarPagoDB(quincena, codigoProveedor, nombreProveedor, KlsTotalLeche, diasEnvioLeche, PromedioKilosLecheDiario, PorcentajeFrecuenciaDiariaEnvioLeche, PorcentajeGrasa, PorcentajeVariacionGrasa, PorcentajeSolidoTotal, PorcentajeVariacionSolidoTotal, PagoPorLeche, PagoPorGrasa, PagoPorSolidosTotales, BonificacionPorFrecuencia, DctoVariacionLeche, DctoVariacionGrasa, DctoVariacionST, PagoTOTAL, MontoRetencion, MontoFINAL);
        verify(pagoRepository).save(expectedPago);
    }
}