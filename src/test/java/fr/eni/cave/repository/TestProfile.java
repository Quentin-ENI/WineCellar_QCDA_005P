package fr.eni.cave.repository;

import fr.eni.cave.bo.avis.Avis;
import fr.eni.cave.bo.avis.BouteilleId;
import fr.eni.cave.bo.profil.Profile;
import fr.eni.cave.bo.profil.ProfileDetails;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class TestProfile {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileDetailsRepository profileDetailsRepository;

    @BeforeAll
    static void initialize(
            @Autowired ProfileRepository profileRepository,
            @Autowired ProfileDetailsRepository profileDetailsRepository
    ) {
        profileRepository.deleteAll();
        profileDetailsRepository.deleteAll();
    }

    @Test
    @Order(1)
    void test_save() {
        HashMap<String, List<String>> interests = new HashMap<>();
        interests.put("Informatique", List.of(
                "Jeux Vidéo",
                "Développement"
        ));

        interests.put("Nature", List.of(
                "Rando",
                "Jardinage"
        ));
        ProfileDetails profileDetails = ProfileDetails.builder()
                .id("1")
                .interests(interests)
                .build();

        ProfileDetails savedProfileDetails = profileDetailsRepository.save(profileDetails);
        assertNotNull(savedProfileDetails);
        assertEquals(savedProfileDetails.getId(), profileDetails.getId());
        assertEquals(savedProfileDetails.getInterests(), interests);

        Profile profile = Profile.builder()
                .pseudo("john@gmail.com")
                .quantiteCommandee(10)
                .profileDetail(profileDetails)
                .build();

        Profile savedProfile = profileRepository.save(profile);

        assertNotNull(savedProfile);
        assertEquals("john@gmail.com",savedProfile.getPseudo());
        assertEquals(profileDetails, savedProfile.getProfileDetail());
        assertEquals(savedProfile, profile);
    }

    @Test
    @Order(2)
    void test_findAll() {
        List<Profile> profiles = profileRepository.findAll();

        profiles.forEach(p -> assertNotNull(p.getPseudo()));

        log.info(profiles.toString());
        assertEquals(1, profiles.size());
    }
}
