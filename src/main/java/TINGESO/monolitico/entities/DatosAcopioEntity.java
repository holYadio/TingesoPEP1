package tingeso.monolitico.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dato_proveedor")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DatosAcopioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fecha;
    private String turno;
    private String proveedor;
    private String kls_leche;
}
