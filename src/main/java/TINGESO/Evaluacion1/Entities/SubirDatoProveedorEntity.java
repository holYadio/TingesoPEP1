package TINGESO.Evaluacion1.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dato_proveedor")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubirDatoProveedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    private String fecha;
    private String turno;
    private String proveedor;
    private String kls_leche;
}