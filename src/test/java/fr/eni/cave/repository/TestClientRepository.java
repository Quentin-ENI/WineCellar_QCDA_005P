package fr.eni.cave.repository;

import fr.eni.cave.bo.client.Adresse;
import fr.eni.cave.bo.client.Client;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TestClientRepository {

    private static final Logger logger = LoggerFactory.getLogger(TestClientRepository.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AdresseRepository adresseRepository;

    @Test
    void test_save_client() {
        // Arrange
        Adresse adresse = Adresse.builder()
                .ville("brest")
                .codePostal("29200")
                .rue("15 rue victor hugo")
                .build();

        Client client = Client.builder()
                .pseudo("bobeponge@email.fr")
                .nom("Eponge")
                .prenom("Bob")
                .password("s3cr3t!")
                .adresse(adresse)
                .build();

        // Act
        Client clientDB = clientRepository.save(client);

        // Assert
        assertEquals("bobeponge@email.fr", clientDB.getPseudo());
        assertEquals(client, clientDB);
        assertNotNull(clientDB.getAdresse().getId());

        // Trace - le mot de passe ne doit pas apparaître
        // Attendu : Client(id=1, pseudo=bobeponge@email.fr, nom=Eponge, prenom=Bob)
        logger.info(clientDB.toString());
    }

    @Test
    void test_delete_client() {
        // Arrange
        Adresse adresse = Adresse.builder()
                .ville("brest")
                .codePostal("29200")
                .rue("15 rue victor hugo")
                .build();

        String pseudo = "bobeponge@email.fr";

        Client client = Client.builder()
                .pseudo(pseudo)
                .nom("Eponge")
                .prenom("Bob")
                .password("s3cr3t!")
                .adresse(adresse)
                .build();

        Client clientDB = clientRepository.save(client);
        Integer idAdresse = clientDB.getAdresse().getId();

        // Trace - le mot de passe ne doit pas apparaître
        logger.info(clientDB.toString());

        // Act
        clientRepository.delete(clientDB);

        // Assert
        Optional<Client> optionalClient = clientRepository.findById(pseudo);
        Optional<Adresse> optionalAdresse = adresseRepository.findById(idAdresse);

        assertTrue(optionalClient.isEmpty());
        assertTrue(optionalAdresse.isEmpty());
    }
}
