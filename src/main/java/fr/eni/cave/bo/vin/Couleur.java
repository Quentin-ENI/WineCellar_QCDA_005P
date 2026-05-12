package fr.eni.cave.bo.vin;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name = "CAV_COLOR")
public class Couleur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COLOR_ID", nullable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String nom;
}
