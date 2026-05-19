package fr.eni.cave.repository;

import fr.eni.cave.bo.avis.Avis;
import fr.eni.cave.bo.avis.BouteilleId;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class TestAvisRepository {

    @Autowired
    private AvisRepository avisRepository;

    @BeforeAll
    static void initialize(
            @Autowired AvisRepository avisRepository
    ) {
        avisRepository.deleteAll();
    }

    @Test
    @Order(1)
    void test_save() {
        BouteilleId bouteilleId = BouteilleId.builder()
                .idBouteille(1)
                .idRegion(1)
                .idCouleur(1)
                .build();

        Avis avis = Avis.builder()
                .id(bouteilleId)
                .note(4)
                .commentaire("Correct")
                .date(LocalDateTime.now())
                .build();

        Avis avisDB =  avisRepository.save(avis);

        log.info(avisDB.toString());

        assertNotNull(avisDB.getId());
        assertEquals(avis.getCommentaire(), avisDB.getCommentaire());
    }

    @Test
    @Order(2)
    void test_findAll() {
        List<Avis> avis = avisRepository.findAll();

        avis.forEach(a -> assertNotNull(a.getId()));

        log.info(avis.toString());
        assertEquals(1, avis.size());
    }
}
