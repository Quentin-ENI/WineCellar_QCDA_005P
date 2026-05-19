package fr.eni.cave.bo.profil;

import fr.eni.cave.bo.avis.BouteilleId;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "profile_details")
public class ProfileDetails {

    @Id
    private String id;

    @Field(name = "interests")
    private HashMap<String, List<String>> interests;
}
