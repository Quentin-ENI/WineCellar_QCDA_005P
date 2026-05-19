package fr.eni.cave.bo.profil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "profile")
public class Profile {

    @Field(name = "login")
    private String pseudo;

    @Field(name = "quantity_ordered")
    private int quantiteCommandee;

    @DBRef
    @Field(name = "profile_detail_id")
    private ProfileDetails profileDetail;
}
