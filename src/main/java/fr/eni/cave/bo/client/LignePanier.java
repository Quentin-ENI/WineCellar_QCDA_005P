package fr.eni.cave.bo.client;

import fr.eni.cave.bo.vin.Bouteille;
import fr.eni.cave.bo.vin.Couleur;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@Entity
@Table(name = "CAV_LINE")
public class LignePanier {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINE_ID")
    private Integer id;

    @Column(name= "QUANTITE" )
    private int qteCommande;

    @Column(name= "PRICE", precision = 2)
    private float prix;

    @ManyToOne(optional = false)
    @JoinColumn(name = "BOTTLE_ID")
    private Bouteille bouteille;
}
