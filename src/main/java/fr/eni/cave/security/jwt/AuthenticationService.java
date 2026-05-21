package fr.eni.cave.security.jwt;

import fr.eni.cave.bo.client.Utilisateur;
import fr.eni.cave.dto.AuthenticationRequestDto;
import fr.eni.cave.dto.AuthenticationResponseDto;
import fr.eni.cave.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private UtilisateurRepository utilisateurRepository;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );

        Utilisateur user = utilisateurRepository.findById(request.getUsername()).orElseThrow();

        String jwt = jwtService.generateToken(user);
        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto(jwt);
        return authenticationResponseDto;
    }
}
