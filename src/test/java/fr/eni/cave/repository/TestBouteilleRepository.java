package fr.eni.cave.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.eni.cave.bo.client.LignePanier;
import fr.eni.cave.bo.client.Panier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.eni.cave.bo.vin.Bouteille;
import fr.eni.cave.bo.vin.Couleur;
import fr.eni.cave.bo.vin.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

@Slf4j
@DataJpaTest
public class TestBouteilleRepository {
	private static final Logger logger = LoggerFactory.getLogger(TestBouteilleRepository.class);

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	BouteilleRepository bouteilleRepository;

	@Autowired
	CouleurRepository couleurRepository;
	
	@Autowired
	RegionRepository regionRepository;
	
	Couleur rouge;
	Couleur blanc;
	Couleur rose;
	
	Region grandEst;
	Region paysDeLaLoire;
	Region nouvelleAquitaine;

	@BeforeEach
	public void initDB() {
		rouge = Couleur
				.builder()
				.nom("Rouge")
				.build();
		
		blanc = Couleur
				.builder()
				.nom("Blanc")
				.build();
				
		rose = Couleur
				.builder()
				.nom("Rosé")
				.build();
				
		couleurRepository.save(rouge);
		couleurRepository.save(blanc);
		couleurRepository.save(rose);
				
		grandEst = 
				Region
				.builder()
				.nom("Grand Est")
				.build();
		
		paysDeLaLoire = 
				Region
				.builder()
				.nom("Pays de la Loire")
				.build();
		
		nouvelleAquitaine = 
				Region
				.builder()
				.nom("Nouvelle Aquitaine")
				.build();
		
		regionRepository.save(grandEst);
		regionRepository.save(paysDeLaLoire);
		regionRepository.save(nouvelleAquitaine);
	}

	@Test // permet de sauver une bouteille avec une couleur et une région
	void test_save(){
		//Arrange
		Bouteille bouteille = Bouteille.builder()
				.nom("B1")
				.prix(20.36f)
				.region(grandEst)
				.couleur(rose)
				.quantite(2)
				.petillant(false)
				.millesime("2026")
				.build();

		//Act
		Bouteille bouteilleDB = bouteilleRepository.save(bouteille);

		//Assert
		assertEquals(bouteilleDB, bouteille);
		assertNotNull(bouteilleDB.getId());

		logger.info(bouteilleDB.toString());
	}

	@Test //permet d’enregistrer plusieurs bouteilles avec des
		// couleurs similaires et des régions similaires
	void test_save_bouteilles_regions_couleurs(){
		//Arrange
		List<Bouteille> bouteilles = jeuDeDonnees();

		//Act
		List<Bouteille> bouteillesDB = bouteilleRepository.saveAll(bouteilles);

		//Assert
		bouteilles.forEach(bouteille -> assertNotNull(bouteille.getId()));

		logger.info(bouteillesDB.toString());
	}

	@Test //permet de vérifier que si on supprime une bouteille,
		// cela ne supprime pas la couleur et la région associées
	void test_delete(){
		//Arrange
		Bouteille bouteille = Bouteille.builder()
				.nom("B1")
				.prix(20.36f)
				.region(grandEst)
				.couleur(rose)
				.quantite(2)
				.petillant(false)
				.millesime("2026")
				.build();

//		Bouteille bouteilleDB = bouteilleRepository.save(bouteille);
		entityManager.persist(bouteille);
		entityManager.flush();

		Integer id = bouteille.getId();

		//Act
		bouteilleRepository.delete(bouteille);

		//Assert
		Bouteille bouteilleDB = entityManager.find(Bouteille.class, id);
		assertNull(bouteilleDB);

		assertNotNull(entityManager.find(Couleur.class, rouge.getId()));
		assertNotNull(entityManager.find(Couleur.class, blanc.getId()));
		assertNotNull(entityManager.find(Couleur.class, rose.getId()));

		assertNotNull(entityManager.find(Region.class, grandEst.getId()));
		assertNotNull(entityManager.find(Region.class, nouvelleAquitaine.getId()));
		assertNotNull(entityManager.find(Region.class, paysDeLaLoire.getId()));
	}

	private List<Bouteille> jeuDeDonnees() {
		List<Bouteille> bouteilles = new ArrayList<>();
		bouteilles.add(Bouteille
				.builder()
				.nom("Blanc du DOMAINE ENI Ecole")
				.millesime("2022")
				.prix(23.95f)
				.quantite(1298)
				.region(paysDeLaLoire)
				.couleur(blanc)
				.build());
		bouteilles.add(Bouteille
				.builder()
				.nom("Rouge du DOMAINE ENI Ecole")
				.millesime("2018")
				.prix(11.45f)
				.quantite(987)
				.region(paysDeLaLoire)
				.couleur(rouge)
				.build());
		bouteilles.add(Bouteille
				.builder()
				.nom("Blanc du DOMAINE ENI Service")
				.millesime("2022")
				.prix(34)
				.petillant(true)
				.quantite(111)
				.region(grandEst)
				.couleur(blanc)
				.build());
		bouteilles.add(Bouteille
				.builder()
				.nom("Rouge du DOMAINE ENI Service")
				.millesime("2012")
				.prix(8.15f)
				.quantite(344)
				.region(paysDeLaLoire)
				.couleur(rouge)
				.build());
		bouteilles.add(Bouteille
				.builder()
				.nom("Rosé du DOMAINE ENI")
				.millesime("2020")
				.prix(33)
				.quantite(1987)
				.region(nouvelleAquitaine)
				.couleur(rose)
				.build());
		return bouteilles;
	}
}
