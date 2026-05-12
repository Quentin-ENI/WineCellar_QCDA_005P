package fr.eni.cave.repository;

import fr.eni.cave.bo.client.Adresse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TestAdresseRepository {
    private static final Logger logger = LoggerFactory.getLogger(TestAdresseRepository.class);

    @Autowired
    private AdresseRepository adresseRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void test_save_adresse() {
        Adresse adresse = Adresse.builder()
                .ville("brest")
                .codePostal("29200")
                .rue("15 rue victor hugo")
                .build();

        Adresse adresseDB = adresseRepository.save(adresse);

        assertEquals("brest", adresseDB.getVille());
        assertEquals(adresse, adresseDB);
        assertNotNull(adresseDB.getId());

        logger.info(adresseDB.toString());
    }

    @Test
    void test_delete_adresse() {

        Adresse adresse = Adresse.builder()
                .ville("brest")
                .codePostal("29200")
                .rue("15 rue victor hugo")
                .build();

        Adresse adresseBD = adresseRepository.save(adresse);

        Integer adresseId = adresseBD.getId();

        adresseRepository.delete(adresseBD);

        Optional<Adresse> optionalAdresse = adresseRepository.findById(adresseId);

        assertTrue(optionalAdresse.isEmpty());
    }
}
