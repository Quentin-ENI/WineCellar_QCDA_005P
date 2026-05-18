package fr.eni.cave.dto;

import fr.eni.cave.bo.vin.Couleur;
import fr.eni.cave.bo.vin.Region;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BouteilleDto {
    private Integer id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    @Size(min = 3, max = 250, message = "Le nom doit contenir entre 3 et 250 caractères.")
    private String nom;

    private boolean petillant;

    @Size(max = 100, message = "Le millésime ne peut pas avoir plus de 100 caractères.")
    private String millesime;

    @Min(value = 1, message = "La quantité doit être supérieure ou égale à 1.")
    private int quantite;

    @Min(value = 1, message = "Le prix doit être supérieur ou égal à 1.")
    private float prix;

    @NotNull(message = "La couleur doit être renseignée.")
    private Integer couleurId;

    @NotNull(message = "La région doit être renseignée.")
    private Integer regionId;
}
