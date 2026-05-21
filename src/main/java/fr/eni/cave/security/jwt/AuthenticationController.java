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
@RequestMapping("/caveavin/auth")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<ResponseApi<AuthenticationResponseDto>> register(
            @RequestBody AuthenticationRequestDto requestDto
    ) {
        AuthenticationResponseDto responseDto =authenticationService.authenticate(requestDto);
        return ResponseEntity.ok(ResponseApi.<AuthenticationResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(responseDto)
                .build());
    }
}