package fr.eni.cave.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AuthenticationResponseDto {
    private String token;
}
