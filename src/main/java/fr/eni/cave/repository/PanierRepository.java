package fr.eni.cave.repository;

import fr.eni.cave.bo.client.Client;
import fr.eni.cave.bo.client.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PanierRepository extends JpaRepository<Panier, Integer> {
    @Query("SELECT p FROM Panier p WHERE p.client = :client AND p.numCommande IS NULL")
    List<Panier> findListPaniers(@Param("client") Client client);

    List<Panier> findPaniersByClientAndNumCommandeIsNull(Client client);

    @Query("SELECT p FROM Panier p WHERE p.client = :client AND p.numCommande IS NOT NULL")
    List<Panier> findListCommandes(@Param("client") Client client);

    List<Panier> findPaniersByClientAndNumCommandeIsNotNull(Client client);
}
