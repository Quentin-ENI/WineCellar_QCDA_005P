package fr.eni.cave.bo.avis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {

    @Field(name = "login")
    private String pseudo;

    @Field(name = "quantity_ordered")
    private int quantiteCommandee;

}
