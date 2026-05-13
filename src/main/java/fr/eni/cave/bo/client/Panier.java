package fr.eni.cave.bo.client;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@Entity
@Table(name = "CAV_SHOPPING_CART")
public class Panier {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOPPING_CART_ID")
    private Integer id;

    @Column(name= "ORDER_NUMBER", length =200 )
    private String numCommande;

    @Column(name= "TOTAL_PRICE", precision = 2 )
    private float prixTotal;

    @Column(name= "PAID")
    private boolean paye;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOPPING_CART_ID", nullable = false)
    private List<LignePanier> lignes = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "CLIENT_ID")
    private Client client;
}
