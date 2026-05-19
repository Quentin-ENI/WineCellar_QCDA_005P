package fr.eni.cave.repository;

import fr.eni.cave.bo.avis.Avis;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AvisRepository extends MongoRepository<Avis, String> {
}
