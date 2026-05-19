package fr.eni.cave.bo.avis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BouteilleId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Field(name = "bottle_id")
    private int idBouteille;

    @Field(name = "color_id")
    private int idCouleur;

    @Field(name = "region_id")
    private int idRegion;

}
