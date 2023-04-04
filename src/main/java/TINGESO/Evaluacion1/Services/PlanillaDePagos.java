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
    @Autowired
    PagoRepository pagoRepository;
    @Autowired
    DatosAcopioService datosAcopioService;

    @Autowired
    DatosLaboratorioService datosLaboratorioService;

    @Autowired
    ProveedorService proveedorService;

    /*
     * Calcula el pago por categoria de proveedor
     * @param proveedor Objeto que tiene la información del proveedor
     * @param datosAcopioEntity Objeto que tiene la información de los datos subidos por el proveedor
     * @return pago
     */
    public double calcularPagoPorCategoria(ProveedorEntity proveedor,double kls_leche) {
        double pago = 0;
        if (proveedor.getCategoria().equals("A")) {
            pago = kls_leche * 700;
        }
        else if (proveedor.getCategoria().equals("B")) {
            pago = kls_leche * 550;
        }
        else if (proveedor.getCategoria().equals("C")) {
            pago = kls_leche * 400;
        }
        else if (proveedor.getCategoria().equals("D")) {
            pago = kls_leche * 250;
        }
        return pago;
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
    public double CalcularBonificacionFrecuencia(ArrayList<DatosAcopioEntity> datosAcopioEntity, double pagoAcopioQuincena){
        double bonificacion = 0;
        int contadorM = 0;
        int contadorT = 0;
        for (DatosAcopioEntity datos : datosAcopioEntity) {
            if (datos.getFecha().contains("M")) {
                contadorM++;
            }
            else if (datos.getFecha().contains("T")) {
                contadorT++;
            }
        }
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

    public double KlsTotalLeche(ArrayList<DatosAcopioEntity> datosAcopio){
        double KlsTotalLeche = 0;
        for (DatosAcopioEntity datos : datosAcopio) {
            KlsTotalLeche += Integer.parseInt(datos.getKls_leche());
        }
        return KlsTotalLeche;
    }

    public double diasEnvioLeche(ArrayList<DatosAcopioEntity> datosAcopio){
        double diasEnvioAcopio = 0;
        for (int i = 0; i < (datosAcopio.size() - 1); i++) {
            if((datosAcopio.get(i).getFecha().equals(datosAcopio.get(i+1).getFecha())) &&
                    !datosAcopio.get(i).getTurno().equals(datosAcopio.get(i+1).getTurno())){
                i++;
            }
            diasEnvioAcopio++;
        }
        return diasEnvioAcopio;
    }

    /*
     * Calcula la retencion de un proveedor
     * @param pago Cantidad que se le debe pagar al proveedor
     * @return retencion Cantidad que se le debe retener al proveedor
     */
    public double calcularRetencion(double pago){
        double retencion = 0;
        double impuesto = 0.13;
        double limite = 950000; // Limite del pago para que se le aplique la retencion al proveedor
        if (pago > limite){
            retencion = pago * impuesto;
        }
        return retencion;
    }

    public double pagoAcopioQuincenal(ArrayList<DatosAcopioEntity> datosAcopio){
        double pagoAcopioQuincenal = 0;

        return pagoAcopioQuincenal;
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
        ArrayList<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();
        String quincena;
        String codigoProveedor;
        String nombreProveedor;
        double KlsTotalLeche;
        String diasEnvioLeche;
        String PromedioKilosLecheDiario;
        String PorcentajeFrecuenciaDiariaEnvioLeche;
        String PorcentajeGrasa;
        String PorcentajeVariacionGrasa;
        String PorcentajeSolidoTotal;
        String PorcentajeVariacionSolidoTotal;
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



        for (int i = 0; i < (datosLaboratorio.size() - 1); i++) {
            double PagoAcopioLeche = 0;
            double DctoTotal=0;
            quincena = datosLaboratorio.get(i).getQuincena();
            codigoProveedor = datosLaboratorio.get(i).getProveedor();
            ArrayList<DatosAcopioEntity> datosAcopioQuincena = datosAcopioService.obtenerDatosAcopioPorQuincenayProveedor(
                    quincena, codigoProveedor);
            nombreProveedor = proveedorService.obtenerNombreProveedor(codigoProveedor);
            KlsTotalLeche = this.KlsTotalLeche(datosAcopioQuincena);
            diasEnvioLeche = String.valueOf(this.diasEnvioLeche(datosAcopioQuincena));
            PromedioKilosLecheDiario = String.valueOf(KlsTotalLeche/15);
            PorcentajeFrecuenciaDiariaEnvioLeche = String.valueOf(this.diasEnvioLeche(datosAcopioQuincena)/15);//Preguntar al profe
            PorcentajeGrasa = datosLaboratorio.get(i).getPorcentaje_grasa();
            PorcentajeVariacionGrasa = datosLaboratorio.get(i).getVariacion_grasa();
            PorcentajeSolidoTotal = datosLaboratorio.get(i).getPorcentaje_solido_total();
            PorcentajeVariacionSolidoTotal = datosLaboratorio.get(i).getVariacion_solido_total();
            PagoPorLeche = this.calcularPagoPorCategoria(
                    proveedorService.obtenerProveedorPorId(codigoProveedor),
                    KlsTotalLeche);
            PagoPorGrasa = this.calcularPagoPorGrasas(PorcentajeGrasa,
                    KlsTotalLeche);
            PagoPorSolidosTotales =this.calcularPagoPorSolidosTotales(PorcentajeSolidoTotal,
                    KlsTotalLeche);
            BonificacionPorFrecuencia = String.valueOf(this.calcularBonificacionPorFrecuencia(datosAcopioQuincena,
                    PagoPorLeche));
            PagoAcopioLeche = PagoPorLeche +
                    PagoPorGrasa +
                    PagoPorSolidosTotales +
                    BonificacionPorFrecuencia;
            DctoVariacionLeche= this.calcularDescuentoPorVariacionLeche(datosAcopioQuincena,
                    PagoAcopioLeche);
            DctoVariacionGrasa = this.calcularDescuentoPorVariacionGrasa(datosAcopioQuincena,
                    PagoAcopioLeche);
            DctoVariacionST= this.calcularDescuentoPorVariacionSolidosTotales(datosAcopioQuincena,
                    PagoAcopioLeche);
            DctoTotal = DctoVariacionLeche +
                    DctoVariacionGrasa +
                    DctoVariacionST;
            PagoTOTAL = PagoAcopioLeche - DctoTotal;
            MontoRetencion= this.calcularRetencion(PagoTOTAL);
            MontoFINAL = String.valueOf(PagoTOTAL - MontoRetencion);

            guardarPago(quincena,
                    codigoProveedor,
                    nombreProveedor,
                    String.valueOf(KlsTotalLeche),
                    diasEnvioLeche,
                    PromedioKilosLecheDiario,
                    PorcentajeFrecuenciaDiariaEnvioLeche,
                    PorcentajeGrasa,
                    PorcentajeVariacionGrasa,
                    PorcentajeSolidoTotal,
                    PorcentajeVariacionSolidoTotal,
                    String.valueOf(PagoPorLeche),
                    String.valueOf(PagoPorGrasa),
                    String.valueOf(PagoPorSolidosTotales),
                    String.valueOf(BonificacionPorFrecuencia),
                    String.valueOf(DctoVariacionLeche),
                    String.valueOf(DctoVariacionGrasa),
                    String.valueOf(DctoVariacionST),
                    String.valueOf(PagoTOTAL),
                    String.valueOf (MontoRetencion),
                    MontoFINAL);
                }
            }
            */
        }
    }

    /*
     * Guarda el pago de un proveedor
     */
    public void guardarPago(String quincena,
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

    /*
     * Obtiene todos los pagos
     * @return pagos Lista de pagos
     */
    public ArrayList<PagoEntity> obtenerPagos(){
        return (ArrayList<PagoEntity>) pagoRepository.findAll();
    }
}

