package fr.eni.cave.repository;

import fr.eni.cave.bo.client.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {
//    @Query("SELECT u FROM Utilisateur u WHERE u.pseudo = :pseudo")
//    List<Utilisateur> filterUserByLogin(@Param("pseudo") String pseudo);

    Utilisateur findUtilisateurByPseudo(String pseudo);

//    @Query("SELECT u FROM Utilisateur u WHERE u.pseudo = :pseudo AND u.password = :password")
//    List<Utilisateur> filterUserByLoginAndPassword(@Param("pseudo") String pseudo, @Param("password") String password);

    Utilisateur findUtilisateurByPseudoAndPassword(String pseudo, String password);
}