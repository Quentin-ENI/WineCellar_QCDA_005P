package fr.eni.cave.repository;

import fr.eni.cave.bo.avis.Avis;
import fr.eni.cave.bo.avis.BouteilleId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AvisRepository extends MongoRepository<Avis, BouteilleId> {
    List<Avis> findAvisByNoteIsLessThan(int note);
    List<Avis> findAvisByNoteIsGreaterThanEqual(int note);
    List<Avis> findAvisById(BouteilleId id);
}
