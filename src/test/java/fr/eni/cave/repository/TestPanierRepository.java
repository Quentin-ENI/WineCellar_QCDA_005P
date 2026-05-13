package fr.eni.cave.repository;

import fr.eni.cave.bo.client.Adresse;
import fr.eni.cave.bo.client.Client;
import fr.eni.cave.bo.client.LignePanier;
import fr.eni.cave.bo.client.Panier;
import fr.eni.cave.bo.vin.Bouteille;
import fr.eni.cave.bo.vin.Couleur;
import fr.eni.cave.bo.vin.Region;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@DataJpaTest
public class TestPanierRepository {
    private static final Logger logger = LoggerFactory.getLogger(TestPanierRepository.class);


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PanierRepository panierRepository;

    Region paysDeLaLoire;
    Couleur blanc;
    List<Bouteille> bouteilles;

    Client tom;


    private Panier panierEnDB() {
        final Panier panier = new Panier();
        final LignePanier lp = LignePanier
                .builder()
                .qteCommande(3)
                .prix(3 * 11.45f)
                .bouteille(bouteilles.getFirst())
                .build();
        panier.getLignes().add(lp);
        panier.setPrixTotal(lp.getPrix());
        panier.setClient(tom);

        entityManager.persist(panier);
        entityManager.flush();

        assertThat(panier.getId()).isGreaterThan(0);
        assertThat(panier.getId()).isGreaterThan(0);

        return panier;
    }

    @BeforeEach
    void initDB() {
        jeuDeDonneesBouteilles();
        jeuDeDonneesPaniersClients();
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
                .nom("Nouvelle Aquitaine")
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
            org.assertj.core.api.Assertions.assertThat(e.getId()).isGreaterThan(0);
        });
        entityManager.flush();

    }

    private void jeuDeDonneesPaniersClients() {
        final Bouteille b1 = bouteilles.get(0);
        final Bouteille b2 = bouteilles.get(1);
        final Bouteille b3 = bouteilles.get(2);

        final List<Panier> paniers = new ArrayList<>();
        final Panier p1 = new Panier();
        final LignePanier lp1 = LignePanier
                .builder()
                .bouteille(b2)
                .qteCommande(3)
                .prix(3 * b2.getPrix())
                .build();
        p1.getLignes().add(lp1);
        p1.setPrixTotal(lp1.getPrix());
        paniers.add(p1);

        final Panier p2 = new Panier();
        final LignePanier lp2 = LignePanier
                .builder()
                .bouteille(b1)
                .qteCommande(10)
                .prix(10 * b1.getPrix())
                .build();
        p2.getLignes().add(lp2);
        p2.setPrixTotal(lp2.getPrix());
        paniers.add(p2);

        final Panier p3 = new Panier();
        final LignePanier lp3 = LignePanier
                .builder()
                .bouteille(b3)
                .qteCommande(4)
                .prix(3 * b3.getPrix())
                .build();
        p3.getLignes().add(lp3);
        p3.setPrixTotal(lp3.getPrix());
        paniers.add(p3);

        Adresse adresse = Adresse.builder()
                .rue("2 rue Georges Perros")
                .codePostal("29000")
                .ville("Quimper")
                .build();

        tom = Client
                .builder()
                .pseudo("tomhanks@email.fr")
                .password("ForrestGump")
                .nom("Hanks")
                .prenom("Tom")
                .adresse(adresse)
                .build();

        entityManager.persist(tom);
        entityManager.flush();

        paniers.forEach(p -> {
            p.setClient(tom);
        });

        // Contexte de la DB
        paniers.forEach(p -> {
            entityManager.persist(p);
            org.assertj.core.api.Assertions.assertThat(p.getId()).isGreaterThan(0);
        });
        entityManager.flush();

        // Indiquer les Panier de ce Client qui ont été commandés
        p1.setPaye(true);
        p1.setNumCommande(tom.getPseudo() + "_" + p1.getId());

        p3.setPaye(true);
        p3.setNumCommande(tom.getPseudo() + "_" + p3.getId());

        entityManager.merge(p1);
        entityManager.merge(p3);
        entityManager.flush();
    }

    @Test
    void test_save_nouvelleLigne_nouveauPanier() {
        //A
        final Panier panier = new Panier();
        final LignePanier lp = LignePanier
                .builder()
                .qteCommande(3)
                .prix(3 * 11.45f)
                .bouteille(bouteilles.getFirst())
                .build();
        panier.getLignes().add(lp);
        panier.setPrixTotal(lp.getPrix());

        panier.setClient(tom);

        //A
        Panier panierDB = panierRepository.save(panier);

        //A
        assertNotNull(panierDB.getId());
        assertEquals(panier, panierDB);
        panierDB
                .getLignes()
                .forEach(lignePanier -> assertNotNull(lignePanier.getId()));

        logger.info(panierDB.toString());
    }

    @Test
    void test_nouvelle_ligne_panier(){
        //A
        Panier panier = panierEnDB();
        LignePanier lignePanier = LignePanier.builder()
                .qteCommande(4)
                .prix(10.50F)
                .bouteille(bouteilles.getFirst())
                .build();

        int nbLignePaniersAvant = panier.getLignes().size();

        panier.getLignes().add(lignePanier);
        panier.setClient(tom);

        //A
        Panier updatedPanier = panierRepository.save(panier);
        updatedPanier
                .getLignes()
                .forEach(lp -> assertNotNull(lp.getId()));

        int nbLignePaniersApres = updatedPanier.getLignes().size();

        assertEquals(nbLignePaniersApres, nbLignePaniersAvant + 1);

        logger.info(panier.toString());

    }

    @Test
    void test_delete() {
        // A
        Panier panier = panierEnDB();
        Integer idPanier = panier.getId();

        // A
        panierRepository.delete(panier);

        // A
        Panier panierDB = entityManager.find(Panier.class, idPanier);
        assertNull(panierDB);
    }

    @Test
    void test_orphanRemoval() {
        // A
        Panier panier = panierEnDB();
        Integer idPanier = panier.getId();
        Integer idLignePanier = panier.getLignes().getFirst().getId();

        // A
        panierRepository.delete(panier);

        // A
        Panier panierDB = entityManager.find(Panier.class, idPanier);
        assertNull(panierDB);

        LignePanier lignePanierDB = entityManager.find(LignePanier.class, idLignePanier);
        assertNull(lignePanierDB);
    }
}
