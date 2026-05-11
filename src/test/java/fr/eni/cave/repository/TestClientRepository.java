package fr.eni.cave.repository;

import fr.eni.cave.bo.Client;
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

    @Test
    void test_save_client() {
        // Arrange
        Client client = Client.builder()
                .pseudo("bobeponge@email.fr")
                .nom("Eponge")
                .prenom("Bob")
                .password("s3cr3t!")
                .build();

        // Act
        Client clientDB = clientRepository.save(client);

        // Assert
        assertNotNull("bobeponge@email.fr", clientDB.getPseudo());
        assertEquals(client, clientDB);

        // Trace - le mot de passe ne doit pas apparaître
        // Attendu : Client(id=1, pseudo=bobeponge@email.fr, nom=Eponge, prenom=Bob)
        logger.info(clientDB.toString());
    }

    @Test
    void test_delete_client() {
        // Arrange
        String pseudo = "bobeponge@email.fr";

        Client client = Client.builder()
                .pseudo(pseudo)
                .nom("Eponge")
                .prenom("Bob")
                .password("s3cr3t!")
                .build();

        Client clientDB = clientRepository.save(client);

        // Trace - le mot de passe ne doit pas apparaître
        logger.info(clientDB.toString());

        // Act
        clientRepository.delete(clientDB);

        // Assert
        Optional<Client> optionalClient = clientRepository.findById(pseudo);

        assertTrue(optionalClient.isEmpty());
    }
}
