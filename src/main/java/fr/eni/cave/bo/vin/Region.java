package fr.eni.cave.bo.vin;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name = "CAV_REGION")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REGION_ID", nullable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true, length = 250)
    private String nom;
}
