package fr.eni.cave.bo.client;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "password")
@Entity
@Table(name = "CAV_USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "LOGIN", length = 255, nullable = false)
    protected String pseudo;

    @Column(name = "PASSWORD", length = 68, nullable = false)
    protected String password;

    @Column(name = "LAST_NAME", length = 90, nullable = false)
    protected String nom;

    @Column(name = "FIRST_NAME", length = 150, nullable = false)
    protected String prenom;


}
