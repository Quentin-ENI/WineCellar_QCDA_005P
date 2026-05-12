package fr.eni.cave.bo.client;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@Entity
@Table(name = "CAV_ADDRESS")
public class Adresse {
    @Column(name = "ADDRESS_ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "STREET",length = 250,nullable = false)
    private String rue;

    @Column(name = "POSTAL_CODE",length = 5, nullable = false)
    private String codePostal;

    @Column(name = "CITY",length = 150, nullable = false)
    private String ville;
}
