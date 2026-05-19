package fr.eni.cave.repository;

import fr.eni.cave.bo.profil.Profile;
import fr.eni.cave.bo.profil.ProfileDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileDetailsRepository extends MongoRepository<ProfileDetails, String> {
}