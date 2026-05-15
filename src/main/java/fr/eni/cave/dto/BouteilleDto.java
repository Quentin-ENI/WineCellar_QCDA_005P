package fr.eni.cave.dto;

import fr.eni.cave.bo.vin.Couleur;
import fr.eni.cave.bo.vin.Region;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BouteilleDto {
    private Integer id;
    private String nom;
    private boolean petillant;
    private String millesime;
    private int quantite;
    private float prix;
    private int couleurId;
    private int regionId;
}
