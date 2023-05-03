package tingeso.monolitico.services;

import tingeso.monolitico.entities.DatosAcopioEntity;
import tingeso.monolitico.entities.DatosLaboratorioEntity;
import tingeso.monolitico.entities.PagoEntity;
import tingeso.monolitico.entities.ProveedorEntity;
import tingeso.monolitico.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
    public List<PagoEntity> obtenerPagos(){
        return pagoRepository.findAll();
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
    public double calcularPagoPorCategoria(ProveedorEntity proveedor,double klsLeche) {
        return switch (proveedor.getCategoria()) {
            case "A" -> klsLeche * 700;
            case "B" -> klsLeche * 550;
            case "C" -> klsLeche * 400;
            case "D" -> klsLeche * 250;
            default -> 0;
        };
    }

    /*
     * Calcula el pago por porcentaje de Grasas
     * @param datosLaboratorioEntity Objeto que tiene la información de los datos subidos por el laboratorio
     * @param datosAcopioEntity Objeto que tiene la información de los datos subidos por el proveedor
     * @return pago Cantidad que se le debe pagar al proveedor asociado al porcentaje de grasas de la leche
     */
    public double calcularPagoPorGrasas(String grasa, double klsLeche) {
        double pago = 0;
        int grasas = Integer.parseInt(grasa);
        if (grasas >= 0 && grasas <= 20) {
            pago = 30;
        }
        else if (grasas >= 21 && grasas <= 45) {
            pago = 80;
        }
        else if (grasas >= 46) {
            pago = 120;
        }
        return pago * klsLeche;
    }

    /*
     * Calcula el pago por porcentaje de Solidos
     * @param datosLaboratorioEntity Objeto que tiene la información de los datos subidos por el laboratorio
     * @param datosAcopioEntity Objeto que tiene la información de los datos subidos por el proveedor
     * @return pago Cantidad que se le debe pagar al proveedor asociado al porcentaje de solidos de la leche
     */
    public double calcularPagoPorSolidosTotales(String solido, double klsLeche) {
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
        return pagoSolido * klsLeche;
    }

    /*
     * Calcula el pago por porcentaje de Proteinas
     * @param datosLaboratorioEntity lista de objetos que tiene la información del acopio de leche
     * @param pagoAcopioQuincena Cantidad que se le debe pagar al proveedor asociado al acopio de leche
     * @return pago Cantidad que se le debe pagar al proveedor dado su frecuencia de entrega
     */
    public double calcularBonificacionPorFrecuencia(List<DatosAcopioEntity> datosAcopioEntity, double pagoAcopioQuincena){
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
        double porcentajeVariacionLeche = datosAcopioService.getVariacionLeche(quincena, codigoProveedor, klsTotalLeche);
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
        List<DatosLaboratorioEntity> datosLaboratorio = datosLaboratorioService.obtenerDatosLaboratorio();
        String quincena;
        String codigoProveedor;
        String nombreProveedor;
        double klsTotalLeche;
        String diasEnvioLeche;
        String promedioKilosLecheDiario;
        String porcentajeFrecuenciaDiariaEnvioLeche;
        String porcentajeGrasa;
        double porcentajeVariacionGrasa;
        String porcentajeSolidoTotal;
        double porcentajeVariacionSolidoTotal;
        double pagoPorLeche;
        double pagoPorGrasa;
        double pagoPorSolidosTotales;
        double bonificacionPorFrecuencia;
        double dctoVariacionLeche;
        double dctoVariacionGrasa;
        double dctoVariacionST;
        double pagoTotal;
        double montoRetencion;
        String montoFinal;
        double pagoAcopioLeche;
        double dctoTotal;
        for (int i = 0; i < (datosLaboratorio.size()); i++) {
            quincena = datosLaboratorio.get(i).getQuincena();
            codigoProveedor = datosLaboratorio.get(i).getProveedor();
            List<DatosAcopioEntity> datosAcopioQuincena = datosAcopioService.obtenerDatosAcopioPorQuincenayProveedor(
                    quincena, codigoProveedor);
            nombreProveedor = proveedorService.obtenerNombreProveedor(codigoProveedor);
            klsTotalLeche = datosAcopioService.klsTotalLeche(datosAcopioQuincena);
            diasEnvioLeche = String.valueOf(datosAcopioService.diasEnvioLeche(datosAcopioQuincena));
            promedioKilosLecheDiario = String.valueOf(klsTotalLeche/15);
            porcentajeFrecuenciaDiariaEnvioLeche = String.valueOf(datosAcopioService.getVariacionLeche(quincena,codigoProveedor,klsTotalLeche));//Preguntar al profe
            porcentajeGrasa = datosLaboratorio.get(i).getPorcentajeGrasa();
            porcentajeVariacionGrasa = datosLaboratorioService.getVariacionGrasa(quincena,
                    codigoProveedor,
                    porcentajeGrasa);
            porcentajeSolidoTotal = datosLaboratorio.get(i).getPorcentajeSolidoTotal();
            porcentajeVariacionSolidoTotal = datosLaboratorioService.getVariacionSolidoTotal(quincena,
                    codigoProveedor,
                    porcentajeSolidoTotal);
            pagoPorLeche = this.calcularPagoPorCategoria(
                    proveedorService.obtenerProveedorPorId(codigoProveedor),
                    klsTotalLeche);
            pagoPorGrasa = this.calcularPagoPorGrasas(porcentajeGrasa,
                    klsTotalLeche);
            pagoPorSolidosTotales =this.calcularPagoPorSolidosTotales(porcentajeSolidoTotal,
                    klsTotalLeche);
            bonificacionPorFrecuencia = this.calcularBonificacionPorFrecuencia(datosAcopioQuincena,
                    pagoPorLeche);
            pagoAcopioLeche = pagoPorLeche +
                    pagoPorGrasa +
                    pagoPorSolidosTotales +
                    bonificacionPorFrecuencia;
            dctoVariacionLeche= this.calcularDescuentoPorVariacionLeche(quincena,
                    codigoProveedor,
                    klsTotalLeche,
                    pagoAcopioLeche);
            dctoVariacionGrasa = this.calcularDescuentoPorVariacionGrasa(porcentajeVariacionGrasa,
                    pagoAcopioLeche);
            dctoVariacionST= this.calcularDescuentoPorVariacionSolidosTotales(porcentajeVariacionSolidoTotal,
                    pagoAcopioLeche);
            dctoTotal = dctoVariacionLeche +
                    dctoVariacionGrasa +
                    dctoVariacionST;
            pagoTotal = pagoAcopioLeche - dctoTotal;
            montoRetencion= this.calcularRetencion(pagoTotal);
            montoFinal = String.valueOf(pagoTotal - montoRetencion);
            guardarPagoDB(quincena,
                    codigoProveedor,
                    nombreProveedor,
                    String.valueOf(klsTotalLeche),
                    diasEnvioLeche,
                    promedioKilosLecheDiario,
                    porcentajeFrecuenciaDiariaEnvioLeche,
                    porcentajeGrasa,
                    String.valueOf(porcentajeVariacionGrasa),
                    porcentajeSolidoTotal,
                    String.valueOf(porcentajeVariacionSolidoTotal),
                    String.valueOf(pagoPorLeche),
                    String.valueOf(pagoPorGrasa),
                    String.valueOf(pagoPorSolidosTotales),
                    String.valueOf(bonificacionPorFrecuencia),
                    String.valueOf(dctoVariacionLeche),
                    String.valueOf(dctoVariacionGrasa),
                    String.valueOf(dctoVariacionST),
                    String.valueOf(pagoTotal),
                    String.valueOf(montoRetencion),
                    montoFinal);
        }
    }

    public void guardarPagoDB(String quincena,
                              String codigoProveedor,
                              String nombreProveedor,
                              String klsTotalLeche,
                              String diasEnvioLeche,
                              String promedioKilosLecheDiario,
                              String porcentajeFrecuenciaDiariaEnvioLeche,
                              String porcentajeGrasa,
                              String porcentajeVariacionGrasa,
                              String porcentajeSolidoTotal,
                              String porcentajeVariacionSolidoTotal,
                              String pagoPorLeche,
                              String pagoPorGrasa,
                              String pagoPorSolidosTotales,
                              String bonificacionPorFrecuencia,
                              String dctoVariacionLeche,
                              String dctoVariacionGrasa,
                              String dctoVariacionST,
                              String pagoTotal,
                              String montoRetencion,
                              String montoFinal){
        PagoEntity pago = new PagoEntity();
        pago.setQuincena(quincena);
        pago.setCodigoProveedor(codigoProveedor);
        pago.setNombreProveedor(nombreProveedor);
        pago.setKlsTotalLeche(klsTotalLeche);
        pago.setDiasEnvioLeche(diasEnvioLeche);
        pago.setPromedioKilosLecheDiario(promedioKilosLecheDiario);
        pago.setPorcentajeFrecuenciaDiariaEnvioLeche(porcentajeFrecuenciaDiariaEnvioLeche);
        pago.setPorcentajeGrasa(porcentajeGrasa);
        pago.setPorcentajeVariacionGrasa(porcentajeVariacionGrasa);
        pago.setPorcentajeSolidoTotal(porcentajeSolidoTotal);
        pago.setPorcentajeVariacionSolidoTotal(porcentajeVariacionSolidoTotal);
        pago.setPagoPorLeche(pagoPorLeche);
        pago.setPagoPorGrasa(pagoPorGrasa);
        pago.setPagoPorSolidosTotales(pagoPorSolidosTotales);
        pago.setBonificacionPorFrecuencia(bonificacionPorFrecuencia);
        pago.setDctoVariacionLeche(dctoVariacionLeche);
        pago.setDctoVariacionGrasa(dctoVariacionGrasa);
        pago.setDctoVariacionST(dctoVariacionST);
        pago.setPagoTotal(pagoTotal);
        pago.setMontoRetencion(montoRetencion);
        pago.setMontoFinal(montoFinal);
        pagoRepository.save(pago);
    }
}
