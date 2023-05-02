package TINGESO.Evaluacion1.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "pagos")
@NoArgsConstructor
@AllArgsConstructor
public class PagoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String quincena;
    private String codigoProveedor;
    private String nombreProveedor;
    private String KlsTotalLeche;
    private String diasEnvioLeche;
    private String PromedioKilosLecheDiario;
    private String PorcentajeFrecuenciaDiariaEnvioLeche;
    private String PorcentajeGrasa;
    private String PorcentajeVariacionGrasa;
    private String PorcentajeSolidoTotal;
    private String PorcentajeVariacionSolidoTotal;
    private String PagoPorLeche;
    private String PagoPorGrasa;
    private String PagoPorSolidosTotales;
    private String BonificacionPorFrecuencia;
    private String DctoVariacionLeche;
    private String DctoVariacionGrasa;
    private String DctoVariacionST;
    private String PagoTOTAL;
    private String MontoRetencion;
    private String MontoFINAL;
}
