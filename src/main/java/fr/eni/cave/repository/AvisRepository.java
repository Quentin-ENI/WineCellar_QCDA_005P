package fr.eni.cave.repository;

import fr.eni.cave.bo.avis.Avis;
import fr.eni.cave.bo.avis.BouteilleId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AvisRepository extends MongoRepository<Avis, BouteilleId> {
    List<Avis> findAvisByNoteIsLessThan(int note);
    List<Avis> findAvisByNoteIsGreaterThanEqual(int note);
    Avis findAvisById(BouteilleId id);
    List<Avis> findAvisByClient_Pseudo(String pseudo);
    List<Avis> findAvisByClient_QuantiteCommandee(int quantiteCommandee);
    List<Avis> findAvisByDateBetween(LocalDateTime dateBefore, LocalDateTime dateAfter);
}
