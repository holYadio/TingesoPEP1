package TINGESO.Evaluacion1.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "dato_proveedor")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DatosAcopioEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fecha;
    private String turno;
    private String proveedor;
    private String kls_leche;
}