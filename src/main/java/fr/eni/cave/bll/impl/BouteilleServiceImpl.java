package fr.eni.cave.bll.impl;

import java.util.List;
import java.util.Optional;

import fr.eni.cave.bll.BouteilleService;
import fr.eni.cave.dto.BouteilleDto;
import fr.eni.cave.exceptions.CouleurException;
import fr.eni.cave.exceptions.RegionException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import fr.eni.cave.bo.vin.Bouteille;
import fr.eni.cave.bo.vin.Couleur;
import fr.eni.cave.bo.vin.Region;
import fr.eni.cave.repository.BouteilleRepository;
import fr.eni.cave.repository.CouleurRepository;
import fr.eni.cave.repository.RegionRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BouteilleServiceImpl implements BouteilleService {
	private BouteilleRepository bRepository;
	private RegionRepository rRepository;
	private CouleurRepository cRepository;


	@Override
	public List<Bouteille> chargerToutesBouteilles() {
		return bRepository.findAll();
	}

	@Override
	public Bouteille chargerBouteilleParId(int idBouteille) {
		// Validation de l'identifiant
		if (idBouteille <= 0) {
			throw new RuntimeException("Identifiant n'existe pas");
		}

		final Optional<Bouteille> opt = bRepository.findById(idBouteille);
		if (opt.isPresent()) {
			return opt.get();
		}
		// Identifiant correspond à aucun enregistrement en base
		throw new RuntimeException("Aucune bouteille ne correspond");
	}

	@Override
	public List<Bouteille> chargerBouteillesParRegion(int idRegion) {
		final Region rDB = validerRegion(idRegion);

		final List<Bouteille> listeDB = bRepository.findBouteillesByRegion(rDB);
		if (listeDB == null || listeDB.isEmpty()) {
			throw new RuntimeException("Aucune bouteille ne correspond");
		}
		return listeDB;		
	}

	private Region validerRegion(int idRegion) {
		// Valider la Region
		if (idRegion <= 0) {
			throw new RuntimeException("Identifiant n'existe pas");
		}

		final Optional<Region> opt = rRepository.findById(idRegion);
		if (opt.isPresent()) {
			return opt.get();
		}
		// Identifiant correspond à aucun enregistrement en base
		throw new RuntimeException("Aucune région ne correspond");
	}

	@Override
	public List<Bouteille> chargerBouteillesParCouleur(int idCouleur) {
		final Couleur cDB = validerCouleur(idCouleur);

		final List<Bouteille> listeDB = bRepository.findBouteillesByCouleur(cDB);
		if (listeDB == null || listeDB.isEmpty()) {
			throw new RuntimeException("Aucune bouteille ne correspond");
		}
		return listeDB;		
	}

	@Override
	public Bouteille create(BouteilleDto bouteilleDto) {
		Bouteille bouteille = buildBouteille(bouteilleDto);
		return saveBouteille(bouteille);
	}


	@Override
	public Bouteille update(BouteilleDto bouteilleDto) {
		if (bouteilleDto.getId() <= 0) {
			throw new RuntimeException("Identifiant n'existe pas");
		}

		if (!bRepository.existsById(bouteilleDto.getId())) {
			this.create(bouteilleDto);
		}
		Bouteille bouteille = buildBouteille(bouteilleDto);
		bouteille.setId(bouteilleDto.getId());
		return saveBouteille(bouteille);
	}

	private Couleur validerCouleur(int idCouleur) {
		if (idCouleur <= 0) {
			throw new RuntimeException("Identifiant n'existe pas");
		}

		final Optional<Couleur> opt = cRepository.findById(idCouleur);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new RuntimeException("Aucune couleur de vin ne correspond");
	}


	private Bouteille buildBouteille(BouteilleDto bouteilleDto) {

		if(bouteilleDto.getCouleurId() <= 0 ) {
			throw new RuntimeException("La couleur n'existe pas");
		}

		if(bouteilleDto.getRegionId() <= 0) {
			throw new RuntimeException("La region n'existe pas");
		}
		Optional<Couleur> couleur = cRepository.findById(bouteilleDto.getCouleurId());
		if(couleur.isEmpty()){
			throw new CouleurException("Le couleur n'existe pas");
		}

		Optional<Region> region = rRepository.findById(bouteilleDto.getRegionId());
		if(region.isEmpty()){
			throw new RegionException("Le region n'existe pas");
		}

		return Bouteille.builder()
				.couleur(couleur.get())
				.region(region.get())
				.prix(bouteilleDto.getPrix())
				.millesime(bouteilleDto.getMillesime())
				.quantite(bouteilleDto.getQuantite())
				.nom(bouteilleDto.getNom())
				.petillant(bouteilleDto.isPetillant())
				.build();
	}

	private Bouteille saveBouteille(Bouteille bouteille) {
		try {
			return bRepository.save(bouteille);
		} catch (DataIntegrityViolationException e) {
			throw e;
		}
	}
}
