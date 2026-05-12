package fr.eni.cave.bo.vin;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name = "CAV_BOTTLE")
public class Bouteille {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOTTLE_ID", nullable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true, length = 250)
    private String nom;

    @Column(name = "SPARKLING")
    private boolean petillant;

    @Column(name = "VINTAGE", length = 100)
    private String millesime;

    @Column(name = "QUANTITY")
    private int quantite;

    @Column(name = "PRICE", precision = 2)
    private float prix;

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "COLOR_ID")
    private Couleur couleur;

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "REGION_ID")
    private Region region;
}
