package fr.eni.cave.repository;

import fr.eni.cave.bo.Avis;
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

    @Test
    @Order(1)
    void test_save() {
        Avis avis = Avis.builder()
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
