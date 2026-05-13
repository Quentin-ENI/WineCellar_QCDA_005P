package fr.eni.cave.repository;

import fr.eni.cave.bo.vin.Bouteille;
import fr.eni.cave.bo.vin.Couleur;
import fr.eni.cave.bo.vin.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BouteilleRepository extends JpaRepository<Bouteille, Integer> {
//    @Query("SELECT b FROM Bouteille b WHERE b.region.id = :regionId")
//    List<Bouteille> filterBottleByRegion(@Param("regionId") Integer regionId);

    List<Bouteille> findBouteillesByRegion(Region region);

//    @Query("SELECT b FROM Bouteille b WHERE b.couleur.id = :couleurId")
//    List<Bouteille> filterBottleByColor(@Param("couleurId") Integer couleurId);

    List<Bouteille> findBouteillesByCouleur(Couleur couleur);
}
