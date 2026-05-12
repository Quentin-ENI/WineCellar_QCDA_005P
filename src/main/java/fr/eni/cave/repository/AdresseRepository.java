
package fr.eni.cave.repository;

import fr.eni.cave.bo.Adresse;
import fr.eni.cave.bo.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdresseRepository extends JpaRepository<Adresse, Integer> {
}
