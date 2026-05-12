package fr.eni.cave.bo;

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
@Table(name = "CAV_ADDRESS")
public class Adresse {
    @EqualsAndHashCode.Include
    @Column(name = "ADDRESS_ID", nullable = false)
    @Id
    private int id;

    @Column(name = "STREET",length = 250,nullable = false)
    private String rue;

    @Column(name = "POSTAL_CODE",length = 5, nullable = false)
    private String code;

    @Column(name = "CITY",length = 150, nullable = false)
    private String ville;

    @OneToOne(mappedBy = "adresse", cascade = CascadeType.ALL, orphanRemoval = true)
    private Client client;
}
