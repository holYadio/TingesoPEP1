package TINGESO.Evaluacion1.Services;

import TINGESO.Evaluacion1.Entities.ProveedorEntity;
import TINGESO.Evaluacion1.Entities.SubirDatoLaboratorioEntity;
import TINGESO.Evaluacion1.Entities.SubirDatoProveedorEntity;
import org.springframework.stereotype.Service;


@Service
public class PlanillaDePagos {
    /*
     * Calcula el pago por categoria de proveedor
     * @param proveedor Objeto que tiene la información del proveedor
     * @param subirDatoProveedorEntity Objeto que tiene la información de los datos subidos por el proveedor
     * @return pago
     */
    public double calcularPagoPorCategoria(ProveedorEntity proveedor, SubirDatoProveedorEntity subirDatoProveedorEntity) {
        double pago = 0;
        int kls_leche = Integer.parseInt(subirDatoProveedorEntity.getKls_leche());
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
     * Calcula el pago total de un proveedor
     * @param proveedor Objeto que tiene la información del proveedor
     * @param subirDatoProveedorEntity Objeto que tiene la información de los datos subidos por el proveedor
     * @param subirDatoLaboratorioEntity Objeto que tiene la información de los datos subidos por el laboratorio
     * @return pagoTotal Cantidad que se le debe pagar al proveedor
     */
    public double CalcularPagoTotal(ProveedorEntity proveedor, SubirDatoProveedorEntity subirDatoProveedorEntity, SubirDatoLaboratorioEntity subirDatoLaboratorioEntity){
        double pagoTotal = 0;
        double pago = calcularPagoPorCategoria(proveedor, subirDatoProveedorEntity);
        double retencion = CalcularRetencion(pago);
        double pagoFinal = calcularPagoFinal(proveedor, subirDatoProveedorEntity, subirDatoLaboratorioEntity);
        pagoTotal = pago - retencion - pagoFinal;
        return pagoTotal;
    }

    /*
     * Calcula la retencion de un proveedor
     * @param pago Cantidad que se le debe pagar al proveedor
     * @return retencion Cantidad que se le debe retener al proveedor
     */
    public double CalcularRetencion(double pago){
        double retencion = 0;
        double impuesto = 0.13;
        if (pago < 950000){
            retencion = 0;
        }
        else if (pago > 950000){
            retencion = pago * impuesto;
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
    public double calcularPagoFinal(ProveedorEntity proveedor, SubirDatoProveedorEntity acopio, SubirDatoLaboratorioEntity datoLaboratorio){
        double pagoFinal = 0;
        return pagoFinal;
    }
}
