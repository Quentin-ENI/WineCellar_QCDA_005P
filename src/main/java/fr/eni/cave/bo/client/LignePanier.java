package fr.eni.cave.bo.client;

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
    private Integer qte_commande;

    @Column(name= "PRICE", precision = 2)
    private float prix;
}
