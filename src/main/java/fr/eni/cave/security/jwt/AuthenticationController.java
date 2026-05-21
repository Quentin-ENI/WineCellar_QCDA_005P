package fr.eni.cave.security.jwt;

import fr.eni.cave.dto.AuthenticationRequestDto;
import fr.eni.cave.dto.AuthenticationResponseDto;
import fr.eni.cave.dto.ResponseApi;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/caveavin/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<ResponseApi<?>> register(
            @RequestBody AuthenticationRequestDto authenticationRequestDto
    ) {
        AuthenticationResponseDto authenticationResponseDto;
        try {
            authenticationResponseDto = authenticationService.authenticate(authenticationRequestDto);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(ResponseApi.builder()
                            .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                            .message(HttpStatus.NOT_ACCEPTABLE.name())
                            .data(e.getMessage())
                            .build());
        }

       return ResponseEntity
               .status(HttpStatus.OK)
               .body(ResponseApi.builder()
                       .statusCode(HttpStatus.OK.value())
                       .message(HttpStatus.OK.name())
                       .data(authenticationResponseDto)
                       .build());
    }
}
