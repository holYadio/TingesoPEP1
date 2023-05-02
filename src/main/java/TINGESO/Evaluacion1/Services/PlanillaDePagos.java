package TINGESO.Evaluacion1.Services;

import TINGESO.Evaluacion1.Entities.DatosAcopioEntity;
import TINGESO.Evaluacion1.Entities.DatosLaboratorioEntity;
import TINGESO.Evaluacion1.Entities.PagoEntity;
import TINGESO.Evaluacion1.Entities.ProveedorEntity;
import TINGESO.Evaluacion1.Repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;



@Service
public class PlanillaDePagos {
    static final double IMPUESTORETENCION = 13;
    static final double LIMITERETENCION = 950000;
    @Autowired
    PagoRepository pagoRepository;
    @Autowired
    DatosAcopioService datosAcopioService;

    @Autowired
    DatosLaboratorioService datosLaboratorioService;

    @Autowired
    ProveedorService proveedorService;

    /*
     * Obtiene todos los pagos
     * @return pagos Lista de pagos
     */
    public ArrayList<PagoEntity> obtenerPagos(){
        return (ArrayList<PagoEntity>) pagoRepository.findAll();
    }

    /*
     * Elimina los pagos en la base de datos
     */
    public void deleteAll(){
        pagoRepository.deleteAll();
    }

    /*
     * Calcula el pago por categoria de proveedor
     * @param proveedor Objeto que tiene la información del proveedor
     * @param datosAcopioEntity Objeto que tiene la información de los datos subidos por el proveedor
     * @return pago
     */
    public double calcularPagoPorCategoria(ProveedorEntity proveedor,double kls_leche) {
        return switch (proveedor.getCategoria()) {
            case "A" -> kls_leche * 700;
            case "B" -> kls_leche * 550;
            case "C" -> kls_leche * 400;
            case "D" -> kls_leche * 250;
            default -> 0;
        };
    }

    /*
     * Calcula el pago por porcentaje de Grasas
     * @param datosLaboratorioEntity Objeto que tiene la información de los datos subidos por el laboratorio
     * @param datosAcopioEntity Objeto que tiene la información de los datos subidos por el proveedor
     * @return pago Cantidad que se le debe pagar al proveedor asociado al porcentaje de grasas de la leche
     */
    public double calcularPagoPorGrasas(String Grasa, double kls_leche) {
        double pago = 0;
        int grasas = Integer.parseInt(Grasa);
        if (grasas >= 0 && grasas <= 20) {
            pago = 30;
        }
        else if (grasas >= 21 && grasas <= 45) {
            pago = 80;
        }
        else if (grasas >= 46) {
            pago = 120;
        }
        return pago * kls_leche;
    }

    /*
     * Calcula el pago por porcentaje de Solidos
     * @param datosLaboratorioEntity Objeto que tiene la información de los datos subidos por el laboratorio
     * @param datosAcopioEntity Objeto que tiene la información de los datos subidos por el proveedor
     * @return pago Cantidad que se le debe pagar al proveedor asociado al porcentaje de solidos de la leche
     */
    public double calcularPagoPorSolidosTotales(String solido, double kls_leche) {
        double pagoSolido = 0;
        int solidos = Integer.parseInt(solido);
        if (solidos >= 0 && solidos <= 7) {
            pagoSolido = -130;
        }
        else if (solidos >= 8 && solidos <= 18) {
            pagoSolido = -90;
        }
        else if (solidos >= 19 && solidos <= 35) {
            pagoSolido = 95;
        }
        else if (solidos >= 36) {
            pagoSolido = 150;
        }
        return pagoSolido*kls_leche;
    }

    /*
     * Calcula el pago por porcentaje de Proteinas
     * @param datosLaboratorioEntity lista de objetos que tiene la información del acopio de leche
     * @param pagoAcopioQuincena Cantidad que se le debe pagar al proveedor asociado al acopio de leche
     * @return pago Cantidad que se le debe pagar al proveedor dado su frecuencia de entrega
     */
    public double calcularBonificacionPorFrecuencia(ArrayList<DatosAcopioEntity> datosAcopioEntity, double pagoAcopioQuincena){
        double bonificacion = 0;
        int contadorM = 0;
        int contadorT = 0;
        for (DatosAcopioEntity datos : datosAcopioEntity) {
            if (datos.getTurno().equals("M")) {
                contadorM++;
            }
            else if (datos.getTurno().equals("T")) {
                contadorT++;
            }
        }
        System.out.println(contadorM);
        System.out.println(contadorT);
        if(contadorM > 10 && contadorT > 10){
            bonificacion = 20;
        }
        else if(contadorM > 10){
            bonificacion = 12;
        }
        else if(contadorT > 10){
            bonificacion = 8;
        }
        return bonificacion*pagoAcopioQuincena/100;
    }

    public double calcularDescuentoPorVariacionLeche(String quincena, String codigoProveedor, double klsTotalLeche, double pagoAcopioLeche) {
        double porcentajeVariacionLeche = datosAcopioService.getVariacion_leche(quincena, codigoProveedor, klsTotalLeche);
        double descuento;
        if ((porcentajeVariacionLeche >= 0) && (porcentajeVariacionLeche <= 8)) {
            descuento = 0;
        } else if ((porcentajeVariacionLeche > 9) && (porcentajeVariacionLeche <= 25)) {
            descuento = 12;
        } else if ((porcentajeVariacionLeche > 25) && (porcentajeVariacionLeche <= 40)) {
            descuento = 20;
        } else {
            descuento = 30;
        }
        return  pagoAcopioLeche * descuento / 100;
    }

    public double calcularDescuentoPorVariacionGrasa(double porcentajeVariacionGrasa, double pagoAcopioLeche) {
        double porcentaje;
        if ((porcentajeVariacionGrasa >= 0) && (porcentajeVariacionGrasa <= 15)) {
            porcentaje = 0;
        } else if ((porcentajeVariacionGrasa > 15) && (porcentajeVariacionGrasa <= 25)) {
            porcentaje = 12;
        } else if ((porcentajeVariacionGrasa > 25) && (porcentajeVariacionGrasa <= 40)) {
            porcentaje = 20;
        } else {
            porcentaje = 30;
        }
        return  pagoAcopioLeche * porcentaje / 100;
    }


    public double calcularDescuentoPorVariacionSolidosTotales(double porcentajeVariacionSolidoTotal, double pagoAcopioLeche) {
        double porcentaje;
        if ((porcentajeVariacionSolidoTotal >= 0) && (porcentajeVariacionSolidoTotal <= 6)) {
            porcentaje = 0;
        } else if ((porcentajeVariacionSolidoTotal > 6) && (porcentajeVariacionSolidoTotal <= 12)) {
            porcentaje = 18;
        } else if ((porcentajeVariacionSolidoTotal > 12) && (porcentajeVariacionSolidoTotal <= 35)) {
            porcentaje = 27;
        } else {
            porcentaje = 45;
        }
        return pagoAcopioLeche * porcentaje / 100;
    }

    /*
     * Calcula la retencion de un proveedor
     * @param pago Cantidad que se le debe pagar al proveedor
     * @return retencion Cantidad que se le debe retener al proveedor
     */
    public double calcularRetencion(double pago){
        double retencion = 0;
        if (pago > LIMITERETENCION){
            retencion = pago * IMPUESTORETENCION/ 100;
        }
        return retencion;
    }

    /*
     * Calcula el pago final de un proveedor
     * @param proveedor Objeto que tiene la información del proveedor
     * @param acopio Objeto que tiene la información de los datos subidos por el proveedor
     * @param datoLaboratorio Objeto que tiene la información de los datos subidos por el laboratorio
     * @return pagoFinal Cantidad que se le debe pagar al proveedor
     */
    public void calcularPagoFinal(){
        ArrayList<DatosLaboratorioEntity> datosLaboratorio = datosLaboratorioService.obtenerDatosLaboratorio();
        String quincena;
        String codigoProveedor;
        String nombreProveedor;
        double KlsTotalLeche;
        String diasEnvioLeche;
        String PromedioKilosLecheDiario;
        String PorcentajeFrecuenciaDiariaEnvioLeche;
        String PorcentajeGrasa;
        double PorcentajeVariacionGrasa;
        String PorcentajeSolidoTotal;
        double PorcentajeVariacionSolidoTotal;
        double PagoPorLeche;
        double PagoPorGrasa;
        double PagoPorSolidosTotales;
        double BonificacionPorFrecuencia;
        double DctoVariacionLeche;
        double DctoVariacionGrasa;
        double DctoVariacionST;
        double PagoTOTAL;
        double MontoRetencion;
        String MontoFINAL;
        for (int i = 0; i < (datosLaboratorio.size()); i++) {
            double PagoAcopioLeche;
            double DctoTotal;
            quincena = datosLaboratorio.get(i).getQuincena();
            codigoProveedor = datosLaboratorio.get(i).getProveedor();
            ArrayList<DatosAcopioEntity> datosAcopioQuincena = datosAcopioService.obtenerDatosAcopioPorQuincenayProveedor(
                    quincena, codigoProveedor);
            nombreProveedor = proveedorService.obtenerNombreProveedor(codigoProveedor);
            KlsTotalLeche = datosAcopioService.KlsTotalLeche(datosAcopioQuincena);
            diasEnvioLeche = String.valueOf(datosAcopioService.diasEnvioLeche(datosAcopioQuincena));
            PromedioKilosLecheDiario = String.valueOf(KlsTotalLeche/15);
            PorcentajeFrecuenciaDiariaEnvioLeche = String.valueOf(datosAcopioService.getVariacion_leche(quincena,codigoProveedor,KlsTotalLeche));//Preguntar al profe
            PorcentajeGrasa = datosLaboratorio.get(i).getPorcentaje_grasa();
            PorcentajeVariacionGrasa = datosLaboratorioService.getVariacion_grasa(quincena,
                    codigoProveedor,
                    PorcentajeGrasa);
            PorcentajeSolidoTotal = datosLaboratorio.get(i).getPorcentaje_solido_total();
            PorcentajeVariacionSolidoTotal = datosLaboratorioService.getVariacion_solido_total(quincena,
                    codigoProveedor,
                    PorcentajeSolidoTotal);
            PagoPorLeche = this.calcularPagoPorCategoria(
                    proveedorService.obtenerProveedorPorId(codigoProveedor),
                    KlsTotalLeche);
            PagoPorGrasa = this.calcularPagoPorGrasas(PorcentajeGrasa,
                    KlsTotalLeche);
            PagoPorSolidosTotales =this.calcularPagoPorSolidosTotales(PorcentajeSolidoTotal,
                    KlsTotalLeche);
            BonificacionPorFrecuencia = this.calcularBonificacionPorFrecuencia(datosAcopioQuincena,
                    PagoPorLeche);
            PagoAcopioLeche = PagoPorLeche +
                    PagoPorGrasa +
                    PagoPorSolidosTotales +
                    BonificacionPorFrecuencia;
            DctoVariacionLeche= this.calcularDescuentoPorVariacionLeche(quincena,
                    codigoProveedor,
                    KlsTotalLeche,
                    PagoAcopioLeche);
            DctoVariacionGrasa = this.calcularDescuentoPorVariacionGrasa(PorcentajeVariacionGrasa,
                    PagoAcopioLeche);
            DctoVariacionST= this.calcularDescuentoPorVariacionSolidosTotales(PorcentajeVariacionSolidoTotal,
                    PagoAcopioLeche);
            DctoTotal = DctoVariacionLeche +
                    DctoVariacionGrasa +
                    DctoVariacionST;
            PagoTOTAL = PagoAcopioLeche - DctoTotal;
            MontoRetencion= this.calcularRetencion(PagoTOTAL);
            MontoFINAL = String.valueOf(PagoTOTAL - MontoRetencion);

            guardarPagoDB(quincena,
                    codigoProveedor,
                    nombreProveedor,
                    String.valueOf(KlsTotalLeche),
                    diasEnvioLeche,
                    PromedioKilosLecheDiario,
                    PorcentajeFrecuenciaDiariaEnvioLeche,
                    PorcentajeGrasa,
                    String.valueOf(PorcentajeVariacionGrasa),
                    PorcentajeSolidoTotal,
                    String.valueOf(PorcentajeVariacionSolidoTotal),
                    String.valueOf(PagoPorLeche),
                    String.valueOf(PagoPorGrasa),
                    String.valueOf(PagoPorSolidosTotales),
                    String.valueOf(BonificacionPorFrecuencia),
                    String.valueOf(DctoVariacionLeche),
                    String.valueOf(DctoVariacionGrasa),
                    String.valueOf(DctoVariacionST),
                    String.valueOf(PagoTOTAL),
                    String.valueOf(MontoRetencion),
                    MontoFINAL);
        }
    }

    public void guardarPagoDB(String quincena,
                              String codigoProveedor,
                              String nombreProveedor,
                              String KlsTotalLeche,
                              String diasEnvioLeche,
                              String PromedioKilosLecheDiario,
                              String PorcentajeFrecuenciaDiariaEnvioLeche,
                              String PorcentajeGrasa,
                              String PorcentajeVariacionGrasa,
                              String PorcentajeSolidoTotal,
                              String PorcentajeVariacionSolidoTotal,
                              String PagoPorLeche,
                              String PagoPorGrasa,
                              String PagoPorSolidosTotales,
                              String BonificacionPorFrecuencia,
                              String DctoVariacionLeche,
                              String DctoVariacionGrasa,
                              String DctoVariacionST,
                              String PagoTOTAL,
                              String MontoRetencion,
                              String MontoFINAL){
        PagoEntity pago = new PagoEntity();
        pago.setQuincena(quincena);
        pago.setCodigoProveedor(codigoProveedor);
        pago.setNombreProveedor(nombreProveedor);
        pago.setKlsTotalLeche(KlsTotalLeche);
        pago.setDiasEnvioLeche(diasEnvioLeche);
        pago.setPromedioKilosLecheDiario(PromedioKilosLecheDiario);
        pago.setPorcentajeFrecuenciaDiariaEnvioLeche(PorcentajeFrecuenciaDiariaEnvioLeche);
        pago.setPorcentajeGrasa(PorcentajeGrasa);
        pago.setPorcentajeVariacionGrasa(PorcentajeVariacionGrasa);
        pago.setPorcentajeSolidoTotal(PorcentajeSolidoTotal);
        pago.setPorcentajeVariacionSolidoTotal(PorcentajeVariacionSolidoTotal);
        pago.setPagoPorLeche(PagoPorLeche);
        pago.setPagoPorGrasa(PagoPorGrasa);
        pago.setPagoPorSolidosTotales(PagoPorSolidosTotales);
        pago.setBonificacionPorFrecuencia(BonificacionPorFrecuencia);
        pago.setDctoVariacionLeche(DctoVariacionLeche);
        pago.setDctoVariacionGrasa(DctoVariacionGrasa);
        pago.setDctoVariacionST(DctoVariacionST);
        pago.setPagoTOTAL(PagoTOTAL);
        pago.setMontoRetencion(MontoRetencion);
        pago.setMontoFINAL(MontoFINAL);
        pagoRepository.save(pago);
    }
}
