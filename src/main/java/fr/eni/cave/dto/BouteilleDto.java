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

    @NotBlank(message = "{bottle.name.blank-error}")
    @Size(min = 3, max = 250, message = "{bottle.name.size-error}")
    private String nom;

    private boolean petillant;

    @Size(max = 100, message = "{bottle.vintage.size-error}")
    private String millesime;

    @Min(value = 1, message = "{bottle.quantity.min-error}")
    private int quantite;

    @Min(value = 1, message = "{bottle.price.min-error}")
    private float prix;

    @NotNull(message = "{bottle.color.not-null}")
    private Integer couleurId;

    @NotNull(message = "{bottle.region.not-null}")
    private Integer regionId;
}
