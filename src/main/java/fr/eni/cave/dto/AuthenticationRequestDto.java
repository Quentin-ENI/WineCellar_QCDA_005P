package fr.eni.cave.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "username")
public class AuthenticationRequestDto {
    private String username;
    private String password;
}