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

//	void insertion_Bouteille_DB() {
//		final List<Bouteille> listeBouteilles = new ArrayList<>();
//		// Création de 3 Bouteille
//
//		listeBouteilles.add(Bouteille
//				.builder()
//				.nom("Vin ENI Edition")
//				.region(regionRepository.getReferenceById(3))
//				.couleur(couleurRepository.getReferenceById(1))
//				.build());
//
//		listeBouteilles.add(Bouteille
//				.builder()
//				.nom("Vin ENI Service")
//				.region(regionRepository.getReferenceById(3))
//				.couleur(couleurRepository.getReferenceById(2))
//				.build());
//
//		listeBouteilles.add(Bouteille
//				.builder()
//				.nom("Vin ENI Ecole")
//				.region(regionRepository.getReferenceById(2))
//				.couleur(couleurRepository.getReferenceById(3))
//				.build());
//
//		listeBouteilles.forEach(b -> {
//			bouteilleRepository.save(b);
//		});
//	}

	void insertion_Avis_DB() {
		// Récupération depuis la base des Bouteille
//		final List<Bouteille> listeBouteilles = bouteilleRepository.findAll();
//		assertThat(listeBouteilles).isNotNull();
//		assertThat(listeBouteilles).isNotEmpty();
//		assertThat(listeBouteilles.size()).isEqualTo(3);



		// Liste de BouteilleId
		final List<BouteilleId> listeBouteillesId = new ArrayList<>();
		listeBouteillesId.add(BouteilleId.builder()
				.idBouteille(bouteilleRepository.findByNom("Vin ENI Edition").getId())
				.idRegion(3)
				.idCouleur(1)
				.build());
		listeBouteillesId.add(BouteilleId.builder()
				.idBouteille(bouteilleRepository.findByNom("Vin ENI Ecole").getId())
				.idRegion(2)
				.idCouleur(3)
				.build());
		listeBouteillesId.add(BouteilleId.builder()
				.idBouteille(bouteilleRepository.findByNom("Vin ENI Service").getId())
				.idRegion(3)
				.idCouleur(2)
				.build());

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
		
		
		for (Profile c : listeProfils) {
			//Faire varier la date :
			LocalDateTime ldf = LocalDateTime.of(2023, 7, 13, 15, 28);
			//Attention, en base l'heure sera en GMT (Heure Française - 2)

			for (int i = 0; i < listeBouteillesId.size(); i++) {
				final BouteilleId b = listeBouteillesId.get(i);


				// Faire varier la quantite du Profile selon la note
				c.setQuantiteCommandee(c.getQuantiteCommandee() * note);
				final Avis avis = Avis
						.builder()
						.note(note)
						.commentaire("Commentaire (" + note + ")")
						.id(b)
						.client(c)
						.date(ldf)
						.build();
				// Sauvegarde de Avis
				avisRepository.save(avis);
				// incrémenter la date
				ldf = ldf.plusDays(10);
			}
			// incrémenter la note
			note++;
		}
	}

//	@Test
//	void test_insertion_DB() {
//		insertion_Bouteille_DB();
//		final List<Bouteille> listeBouteilles = bouteilleRepository.findAll();
//		assertThat(listeBouteilles).isNotNull();
//		assertThat(listeBouteilles).isNotEmpty();
//		assertThat(listeBouteilles.size()).isEqualTo(3);
//
//		insertion_Avis_DB();
//		final List<Avis> listeAvis = avisRepository.findAll();
//		assertThat(listeAvis).isNotNull();
//		assertThat(listeAvis).isNotEmpty();
//		assertThat(listeAvis.size()).isEqualTo(9);
//	}

	@Test
	void test_find_by_note_is_less_than(){
		//A
		insertion_Avis_DB();
		//A
		List<Avis> avisList = avisRepository.findAvisByNoteIsLessThan(5);
		//A
		assertNotNull(avisList);
		assertEquals(3, avisList.size());
		logger.info(avisList.toString());
	}

	@Test
	void test_find_by_note_is_greater_than(){
		//A
		insertion_Avis_DB();
		//A
		List<Avis> avisList = avisRepository.findAvisByNoteIsGreaterThanEqual(4);
		//A
		assertNotNull(avisList);
		assertEquals(3, avisList.size());
		logger.info(avisList.toString());
	}

	@Test
	void test_find_avis_by_id(){
		//A
		insertion_Avis_DB();
		//A
		Avis avis = avisRepository.findAvisById(

		);
		//A
		assertNotNull(avis);
		logger.info(avis.toString());
	}


}
