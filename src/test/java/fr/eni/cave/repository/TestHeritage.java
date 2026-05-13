package fr.eni.cave.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

import fr.eni.cave.bo.client.Proprio;
import fr.eni.cave.bo.client.Utilisateur;
import fr.eni.cave.repository.ClientRepository;
import fr.eni.cave.repository.ProprioRepository;
import fr.eni.cave.repository.UtilisateurRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;


import fr.eni.cave.bo.client.Client;
import fr.eni.cave.bo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

@Slf4j
@DataJpaTest
public class TestHeritage {
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
				.build());

		// Contexte de la DB
		utilisateurs.forEach(e -> {
			entityManager.persist(e);
		});
	}


}
