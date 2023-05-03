package tingeso.monolitico.entities;


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
    private Integer id;
    private String proveedor;
    private String porcentajeGrasa;
    private String porcentajeSolidoTotal;
    private String quincena;
}
