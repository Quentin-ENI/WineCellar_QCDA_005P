package fr.eni.cave.repository;

import fr.eni.cave.bo.client.Adresse;
import fr.eni.cave.bo.client.Client;
import fr.eni.cave.bo.client.Proprio;
import fr.eni.cave.bo.client.Utilisateur;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DataJpaTest
public class TestHeritage {
	private final Logger logger = LoggerFactory.getLogger(TestHeritage.class);

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	UtilisateurRepository utilisateurRepository;

	@Autowired
	ProprioRepository proprioRepository;

	@Autowired
	ClientRepository clientRepository;

	@BeforeEach
	public void initDB() {
		Adresse adresse = Adresse.builder()
				.rue("2 rue Georges Perros")
				.codePostal("29000")
				.ville("Quimper")
				.build();

		List<Utilisateur> utilisateurs = new ArrayList<>();
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
				.siret("12345678901234")
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
	void test_findAll_utilisateur() {
		// A
		// A
		List<Utilisateur> users = utilisateurRepository.findAll();
		// A
		assertThat(users.size()).isEqualTo(3);

		logger.info(users.toString());
	}

	@Test
	void test_findAll_proprio() {
		// A
		// A
		List<Proprio> owners = proprioRepository.findAll();
		// A
		assertThat(owners.size()).isEqualTo(1);
		assertEquals("georgelucas@email.fr", owners.getFirst().getPseudo());
		assertNotNull(owners.getFirst().getSiret());

		logger.info(owners.getFirst().toString());
	}

	@Test
	void test_findAll_client() {
		// A
		// A
		List<Client> customers = clientRepository.findAll();
		// A
		assertThat(customers.size()).isEqualTo(1);
		assertEquals("natalieportman@email.fr", customers.getFirst().getPseudo());

		logger.info(customers.getFirst().toString());
	}
}
