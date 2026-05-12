package fr.eni.cave.repository;

import fr.eni.cave.bo.client.LignePanier;
import fr.eni.cave.bo.client.Panier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Slf4j
@DataJpaTest
public class TestPanierRepository {
    private static final Logger logger = LoggerFactory.getLogger(TestPanierRepository.class);


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PanierRepository repository;

    private Panier panierEnDB() {
        final Panier panier = new Panier();
        final LignePanier lp = LignePanier
                .builder()
                .qte_commande(3)
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
        Panier panier = panierEnDB();

        //A
        Panier panierDB = repository.save(panier);

        //A
        assertEquals(panier, panierDB);
        assertNotNull(panierDB.getId());

        logger.info(panierDB.toString());
    }

    @Test
    void test_nouvelle_ligne_panier(){
        //A
        final Panier panier = new Panier();
        final LignePanier lp = LignePanier
                .builder()
                .qte_commande(3)
                .prix(3 * 11.45f)
                .build();
        panier.getLignes().add(lp);
        panier.setPrixTotal(lp.getPrix());

        entityManager.persist(panier);
        entityManager.flush();

        assertThat(panier.getId()).isGreaterThan(0);
        assertThat(panier.getId()).isGreaterThan(0);

        //A


    }
}
