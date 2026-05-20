package fr.eni.cave.repository;

import static org.assertj.core.api.Assertions.assertThat;import static org.junit.jupiter.api.Assertions.assertEquals;import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.cave.bo.avis.Avis;import fr.eni.cave.bo.avis.BouteilleId;
import fr.eni.cave.bo.client.Client;
import fr.eni.cave.bo.profil.Profile;import fr.eni.cave.bo.vin.Bouteille;
import fr.eni.cave.bo.vin.Couleur;
import fr.eni.cave.bo.vin.Region;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class DataPourRequetes {
	private static final Logger logger = LoggerFactory.getLogger(DataPourRequetes.class);


	@Autowired
	BouteilleRepository bouteilleRepository;

	@Autowired
	AvisRepository avisRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CouleurRepository couleurRepository;
	@Autowired
	private ProfileRepository profileRepository;


	void insertion_Avis_DB() {

		profileRepository.deleteAll();
		avisRepository.deleteAll();

		// Liste de Client
		final List<Profile> listeProfils = new ArrayList<>();
		// Création de 3 Client
		listeProfils.add(Profile
				.builder()
				.pseudo("bobeponge@email.fr")
				.quantiteCommandee(11)
				.build());
		listeProfils.add(Profile
				.builder()
				.pseudo("patricketoile@email.fr")
				.quantiteCommandee(12)
				.build());
		listeProfils.add(Profile
				.builder()
				.pseudo("carlotentacule@email.fr")
				.quantiteCommandee(25)
				.build());

		// Ajout d'Avis par Profile sur chaque Bouteille
		// Faire varier la note
		int note = 2;
		int nbBouteilles = 9;

		for (Profile profile : listeProfils) {
			profileRepository.save(profile);

			//Faire varier la date :
			LocalDateTime date = LocalDateTime.of(2023, 7, 13, 15, 28);

			//Attention, en base l'heure sera en GMT (Heure Française - 2)
			for (int i = 0; i < nbBouteilles; i++) {
				final BouteilleId bouteilleId = BouteilleId.builder()
						.idBouteille(i)
						.idRegion(i)
						.idCouleur(i)
						.build();

				// Faire varier la quantite du Profile selon la note
				profile.setQuantiteCommandee(profile.getQuantiteCommandee() * note);
				final Avis avis = Avis
						.builder()
						.note(note)
						.commentaire("Commentaire (" + note + ")")
						.id(bouteilleId)
						.client(profile)
						.date(date)
						.build();

				// Sauvegarde de Avis
				avisRepository.save(avis);
				// incrémenter la date
				date = date.plusDays(10);
				// incrémenter la note
				note = ((note + 1) % 5) + 1;
			}
		}
	}

	@Test
	void test_find_by_note_is_less_than(){
		//A
		insertion_Avis_DB();
		//A
		List<Avis> avisList = avisRepository.findAvisByNoteIsLessThan(3);

		//A
		assertNotNull(avisList);
		logger.info(avisList.toString());
		assertEquals(3, avisList.size());
	}

	@Test
	void test_find_by_note_is_greater_than(){
		//A
		insertion_Avis_DB();
		//A
		List<Avis> avisList = avisRepository.findAvisByNoteIsGreaterThanEqual(3);
		//A
		assertNotNull(avisList);
		assertEquals(6, avisList.size());
		logger.info(avisList.toString());
	}

	@Test
	void test_find_avis_by_id(){
		//A
		insertion_Avis_DB();
		BouteilleId id = BouteilleId.builder()
				.idBouteille(2)
				.idCouleur(2)
				.idRegion(2)
				.build();

		//A
		Avis avis = avisRepository.findAvisById(id);

		//A
		assertNotNull(avis);
		logger.info(avis.toString());
	}

	@Test
	void test_find_avis_by_client_pseudo(){
		//A
		insertion_Avis_DB();
		String pseudo = "carlotentacule@email.fr";

		//A
		List<Avis> avis = avisRepository.findAvisByClient_Pseudo(pseudo);

		//A
		assertEquals(9, avis.size());
		logger.info(avis.toString());
	}

	@Test
	void test_find_avis_by_client_quantiteCommandee(){
		//A
		insertion_Avis_DB();
		int quantiteCommandee = 75;

		//A
		List<Avis> avis = avisRepository.findAvisByClient_QuantiteCommandee(quantiteCommandee);

		//A
		assertEquals(1, avis.size());
		logger.info(avis.toString());
	}

	@Test
	void test_find_avis_by_client_date(){
		//A
		insertion_Avis_DB();

		LocalDateTime dateBefore = LocalDateTime.of(2023, 7, 10, 0, 0);
		LocalDateTime dateAfter = LocalDateTime.of(2023, 7, 15, 0, 0);

		//A
		List<Avis> avis = avisRepository.findAvisByDateBetween(dateBefore, dateAfter);

		//A
		logger.info(avis.toString());
		assertEquals(1, avis.size());
	}
}
