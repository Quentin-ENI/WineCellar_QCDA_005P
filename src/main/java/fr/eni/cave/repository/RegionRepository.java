package fr.eni.cave.repository;

import fr.eni.cave.bo.vin.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Integer> {
}
