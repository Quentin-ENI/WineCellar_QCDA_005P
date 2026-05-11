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
    @Column(name = "LOGIN", nullable = false)
    @Id
    private String pseudo;

    @Column(name = "LAST_NAME", nullable = false)
    private String nom;

    @Column(name = "FIRST_NAME", nullable = false)
    private String prenom;

    @Column(name = "PASSWORD", nullable = false)
    private String password;
}
