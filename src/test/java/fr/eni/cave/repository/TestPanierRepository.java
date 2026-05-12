package fr.eni.cave.repository;

import fr.eni.cave.bo.client.LignePanier;
import fr.eni.cave.bo.client.Panier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

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

    private Panier panierEnDB() {
        final Panier panier = new Panier();
        final LignePanier lp = LignePanier
                .builder()
                .qteCommande(3)
                .prix(3 * 11.45f)
                .build();
        panier.getLignes().add(lp);
        panier.setPrixTotal(lp.getPrix());

        entityManager.persist(panier);
        entityManager.flush();

        assertThat(panier.getId()).isGreaterThan(0);
        assertThat(panier.getId()).isGreaterThan(0);

        return panier;
    }

    @Test
    void test_save_nouvelleLigne_nouveauPanier() {
        //A
        final Panier panier = new Panier();
        final LignePanier lp = LignePanier
                .builder()
                .qteCommande(3)
                .prix(3 * 11.45f)
                .build();
        panier.getLignes().add(lp);
        panier.setPrixTotal(lp.getPrix());

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
                .build();

        int nbLignePaniersAvant = panier.getLignes().size();

        panier.getLignes().add(lignePanier);

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
