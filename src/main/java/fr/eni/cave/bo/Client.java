package fr.eni.cave.bo;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "password")
@Builder
@Entity
@Table(name = "CAV_CLIENT")
public class Client {
    @EqualsAndHashCode.Include
    @Column(name = "LOGIN",length = 255, nullable = false)
    @Id
    private String pseudo;

    @Column(name = "LAST_NAME",length = 90, nullable = false)
    private String nom;

    @Column(name = "FIRST_NAME",length = 150, nullable = false)
    private String prenom;

    @Column(name = "PASSWORD",length = 68, nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    private Adresse adresse;
}
