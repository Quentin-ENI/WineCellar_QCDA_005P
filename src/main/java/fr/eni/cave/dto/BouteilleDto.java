package fr.eni.cave.dto;

import fr.eni.cave.bo.vin.Couleur;
import fr.eni.cave.bo.vin.Region;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BouteilleDto {
    private Integer id;

    @Size(min = 3, max = 200, message = "Le nom doit contenir entre 3 et 200 caractères.")
    private String nom;

    private boolean petillant;

    @Size(max = 4, message = "Le millésime doit être composé de 4 chiffres.")
    private String millesime;

    @Min(value = 1, message = "La quantité doit être supérieure à 1.")
    private int quantite;

    @Min(value = 1, message = "La quantité doit être supérieure à 1.")
    private float prix;

    @NotNull(message = "La couleur ne peut pas être nulle.")
    private int couleurId;

    @NotNull(message = "La région ne peut pas être nulle.")
    private int regionId;
}
