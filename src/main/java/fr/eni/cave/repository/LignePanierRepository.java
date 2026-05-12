package fr.eni.cave.repository;

import fr.eni.cave.bo.client.LignePanier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LignePanierRepository extends JpaRepository<LignePanier, Integer> {
}
