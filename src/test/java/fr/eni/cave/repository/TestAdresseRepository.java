package fr.eni.cave.repository;

import fr.eni.cave.bo.Adresse;
import fr.eni.cave.bo.Client;
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
                .code("29200")
                .rue("15 rue victor hugo")
                .build();

        Adresse adresseDB = adresseRepository.save(adresse);

        assertNotNull("brest", adresseDB.getVille());
        assertEquals(adresse, adresseDB);


        logger.info(adresseDB.toString());
    }

    @Test
    void test_save_client_with_adresse() {
        Adresse adresse = Adresse.builder()
                .ville("brest")
                .code("29200")
                .rue("15 rue victor hugo")
                .build();

        Adresse adresseDB = adresseRepository.save(adresse);

        assertNotNull("brest", adresseDB.getVille());
        assertEquals(adresse, adresseDB);


        logger.info(adresseDB.toString());

        Client client = Client.builder()
                .pseudo("bobeponge@email.fr")
                .nom("Eponge")
                .prenom("Bob")
                .password("s3cr3t!")
                .adresse(adresse)
                .build();

        Client clientDB = clientRepository.save(client);
        assertNotNull("bobeponge@email.fr", clientDB.getPseudo());
        assertEquals(client, clientDB);
        assertEquals(adresse, clientDB.getAdresse());

        logger.info(clientDB.toString());


    }

    @Test
    void test_delete_adresse() {

        int adresseId = 1;

        Adresse adresse = Adresse.builder()
                .id(adresseId)
                .ville("brest")
                .code("29200")
                .rue("15 rue victor hugo")
                .build();

        Adresse adresseBD = adresseRepository.save(adresse);

        adresseRepository.delete(adresseBD);

        Optional<Adresse> optionalAdresse = adresseRepository.findById(adresseId);

        assertTrue(optionalAdresse.isEmpty());
    }

    @Test
    void test_delete_client_with_adresse() {
        int adresseId = 1;
        String pseudo = "bobeponge@email.fr";

        Adresse adresse = Adresse.builder()
                .id(adresseId)
                .ville("Brest")
                .code("29200")
                .rue("15 rue Victor Hugo")
                .build();

        Adresse adresseDB = adresseRepository.save(adresse);

        Client client = Client.builder()
                .pseudo(pseudo)
                .nom("Eponge")
                .prenom("Bob")
                .password("s3cr3t!")
                .adresse(adresseDB)
                .build();

        Client clientDB = clientRepository.save(client);

        clientRepository.delete(clientDB);

        Optional<Client> optionalClient = clientRepository.findById(pseudo);
        assertTrue(optionalClient.isEmpty());

        Optional<Adresse> optionalAdresse = adresseRepository.findById(adresseId);
        assertFalse(optionalAdresse.isEmpty());
    }

    @Test
    void test_delete_adresse_with_client() {
        int adresseId = 1;
        String pseudo = "bobeponge@email.fr";

        Adresse adresse = Adresse.builder()
                .id(adresseId)
                .ville("Brest")
                .code("29200")
                .rue("15 rue Victor Hugo")
                .build();

        Adresse adresseDB = adresseRepository.save(adresse);

        Client client = Client.builder()
                .pseudo(pseudo)
                .nom("Eponge")
                .prenom("Bob")
                .password("s3cr3t!")
                .adresse(adresseDB)
                .build();

        clientRepository.save(client);

        adresseDB.setClient(client);
        adresseRepository.save(adresseDB);

        adresseRepository.delete(adresseDB);

        Optional<Adresse> optionalAdresse = adresseRepository.findById(adresseId);
        assertTrue(optionalAdresse.isEmpty());

        Optional<Client> optionalClient = clientRepository.findById(pseudo);
        assertTrue(optionalClient.isEmpty());
    }

}
