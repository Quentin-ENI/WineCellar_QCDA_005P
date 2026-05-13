package fr.eni.cave.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.filter;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import fr.eni.cave.bo.client.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.eni.cave.bo.vin.Bouteille;
import fr.eni.cave.bo.vin.Couleur;
import fr.eni.cave.bo.vin.Region;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

@DataJpaTest
public class TestRequetes {
	private static final Logger logger = LoggerFactory.getLogger(TestRequetes.class);

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	UtilisateurRepository utilisateurRepository;

	@Autowired
	BouteilleRepository bouteilleRepository;

	@Autowired
	LignePanierRepository lignePanierRepository;

	Region paysDeLaLoire;
	Couleur blanc;
	List<Bouteille> bouteilles;
    @Autowired
    private PanierRepository panierRepository;

	@BeforeEach
	void initDB() {
		jeuDeDonneesBouteilles();
		jeuDeDonneesUtilisateur();
	}

	private void jeuDeDonneesBouteilles() {
		final Couleur rouge = Couleur
				.builder()
				.nom("Rouge")
				.build();

		blanc = Couleur
				.builder()
				.nom("Blanc")
				.build();

		final Couleur rose = Couleur
				.builder()
				.nom("Rosé")
				.build();

		entityManager.persist(rouge);
		entityManager.persist(blanc);
		entityManager.persist(rose);
		entityManager.flush();

		final Region grandEst = Region
				.builder()
				.nom("Grand Est")
				.build();

		paysDeLaLoire = Region
				.builder()
				.nom("Pays de la Loire")
				.build();

		final Region nouvelleAquitaine = Region
				.builder()
				.nom("Nouvelle-Aquitaine")
				.build();

		entityManager.persist(grandEst);
		entityManager.persist(paysDeLaLoire);
		entityManager.persist(nouvelleAquitaine);
		entityManager.flush();

		bouteilles = new ArrayList<>();
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

		bouteilles.forEach(e -> {
			entityManager.persist(e);
			// Vérification de l'identifiant
			assertThat(e.getId()).isGreaterThan(0);
		});
		entityManager.flush();

	}

	private void jeuDeDonneesUtilisateur() {
		Adresse adresse = Adresse.builder()
				.rue("2 rue Georges Perros")
				.codePostal("29000")
				.ville("Quimper")
				.build();

		final List<Utilisateur> utilisateurs = new ArrayList<>();
		utilisateurs.add(Utilisateur
				.builder()
				.pseudo("harrisonford@email.fr")
				.password("IndianaJones3")
				.nom("Ford")
				.prenom("Harrison")
				.build());

		utilisateurs.add(Proprio
				.builder()
				.pseudo("georgelucas@email.fr")
				.password("Réalisateur&Producteur")
				.nom("Lucas")
				.prenom("George")
				.build());

		utilisateurs.add(Client
				.builder()
				.pseudo("natalieportman@email.fr")
				.password("MarsAttacks!")
				.nom("Portman")
				.prenom("Natalie")
				.adresse(adresse)
				.build());

		// Contexte de la DB
		utilisateurs.forEach(e -> {
			entityManager.persist(e);
		});
		entityManager.flush();
	}

	@Test
	void test_save_ligneCommande_bouteille() {
		//A
		Bouteille bouteille = bouteilles.getFirst();

		Adresse adresse = Adresse.builder()
				.rue("2 rue Georges Perros")
				.codePostal("29000")
				.ville("Quimper")
				.build();

		Client tom = Client
				.builder()
				.pseudo("tomhanks@email.fr")
				.password("ForrestGump")
				.nom("Hanks")
				.prenom("Tom")
				.adresse(adresse)
				.build();

		Panier panier = new Panier();
		LignePanier lignePanier = LignePanier.builder()
				.prix(bouteille.getPrix())
				.qteCommande(bouteille.getQuantite())
				.bouteille(bouteille)
				.build();

		LignePanier lignePanier2 = LignePanier.builder()
				.prix(bouteille.getPrix())
				.qteCommande(bouteille.getQuantite())
				.bouteille(bouteille)
				.build();

		panier.setClient(tom);
		panier.getLignes().add(lignePanier);

		entityManager.persist(bouteille);
		entityManager.persist(tom);
		entityManager.persist(panier);
		entityManager.flush();

		panier.getLignes().add(lignePanier2);

		//A
		Panier panierDB = panierRepository.save(panier);

		//A
		panier.getLignes().forEach(
				lp -> {
					assertNotNull(lp.getId());
					logger.info(lp.toString());
				}
		);
	}

	@Test
	void test_delete_ligneCommande_bouteille() {
		//A
		Bouteille bouteille = bouteilles.getFirst();

		Adresse adresse = Adresse.builder()
				.rue("2 rue Georges Perros")
				.codePostal("29000")
				.ville("Quimper")
				.build();

		Client tom = Client
				.builder()
				.pseudo("tomhanks@email.fr")
				.password("ForrestGump")
				.nom("Hanks")
				.prenom("Tom")
				.adresse(adresse)
				.build();

		Panier panier = new Panier();
		LignePanier lignePanier = LignePanier.builder()
				.prix(bouteille.getPrix())
				.qteCommande(bouteille.getQuantite())
				.bouteille(bouteille)
				.build();
		panier.getLignes().add(lignePanier);
		panier.setClient(tom);

		entityManager.persist(bouteille);
		entityManager.persist(adresse);
		entityManager.persist(tom);
		entityManager.persist(panier);
		entityManager.flush();

		Integer id = lignePanier.getId();

		//A
		lignePanierRepository.delete(lignePanier);

		//A
		LignePanier lignePanierDB = entityManager.find(LignePanier.class, id);
		assertNull(lignePanierDB);

		assertNotNull(entityManager.find(Bouteille.class, bouteille.getId()));
	}

	@Test
	void test_JPQL_bouteille_region() {
		// A

		// A
		List<Bouteille> filterBouteilles = bouteilleRepository.findBouteillesByRegion(paysDeLaLoire);

		// A
		assertEquals(3, filterBouteilles.size());
		filterBouteilles.forEach(b -> {
			assertEquals(b.getRegion(), paysDeLaLoire);
			logger.info(b.toString());
		});
	}

	@Test
	void test_JPQL_bouteille_couleur() {
		// A

		// A
		List<Bouteille> filterBouteilles = bouteilleRepository.findBouteillesByCouleur(blanc);

		// A
		assertEquals(2, filterBouteilles.size());
		filterBouteilles.forEach(b -> {
			assertEquals(b.getCouleur(), blanc);
			logger.info(b.toString());
		});
	}

	@Test
	void test_JPQL_utilisateur_pseudo() {
		// A

		// A
		Utilisateur user = utilisateurRepository.findUtilisateurByPseudo("georgelucas@email.fr");

		// A
		assertEquals("Lucas", user.getNom());
		assertEquals("George", user.getPrenom());
	}

	@Test
	void test_JPQL_utilisateur_pseudo_password() {
		// A

		// A
		Utilisateur user = utilisateurRepository.findUtilisateurByPseudoAndPassword("harrisonford@email.fr", "IndianaJones3");

		// A
		assertEquals("Ford", user.getNom());
		assertEquals("Harrison", user.getPrenom());
	}
}
