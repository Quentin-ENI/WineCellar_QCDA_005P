package fr.eni.cave.repository;

import fr.eni.cave.bo.profil.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile, String> {
}