package fr.eni.cave.repository;

import fr.eni.cave.bo.avis.Avis;
import fr.eni.cave.bo.avis.BouteilleId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AvisRepository extends MongoRepository<Avis, BouteilleId> {
}
