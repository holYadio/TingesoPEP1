package TINGESO.Evaluacion1.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "dato_laboratorio")
@NoArgsConstructor
@AllArgsConstructor
public class DatosLaboratorioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    private String proveedor;
    private String porcentaje_grasa;
    private String porcentaje_solido_total;
    private String quincena;
}
