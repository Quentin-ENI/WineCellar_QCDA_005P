package fr.eni.cave.bo;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "reviews")
public class Avis {

    @Id
    private String id;

    @Field(name = "note")
    private int note;

    @Field(name = "commentary")
    private String commentaire;

    @Field(name = "date")
    private LocalDateTime date;
}
