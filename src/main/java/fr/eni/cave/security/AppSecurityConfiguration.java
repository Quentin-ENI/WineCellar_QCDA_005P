package fr.eni.cave.security;

import jakarta.servlet.Filter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class AppSecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AppSecurityConfiguration.class);

    private Filter jwtAuthenticationFilter;
    private AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {
        http.authorizeHttpRequests(auth -> auth
                // ===== VISITEUR (public)
                .requestMatchers(HttpMethod.POST, "/caveavin/auth").permitAll()

                .requestMatchers(HttpMethod.GET, "/caveavin/bouteilles/**").permitAll()


                // ===== CLIENT + OWNER
                .requestMatchers(HttpMethod.GET, "/caveavin/paniers/**")
                .hasAnyAuthority("ROLE_CLIENT", "ROLE_OWNER")

                // ===== CLIENT ONLY
                .requestMatchers(HttpMethod.POST, "/caveavin/paniers/**")
                .hasAuthority("ROLE_CLIENT")

                .requestMatchers(HttpMethod.PUT, "/caveavin/paniers/**")
                .hasAuthority("ROLE_CLIENT")

                // ===== OWNER ONLY
                .requestMatchers(HttpMethod.POST, "/caveavin/bouteilles/**")
                .hasAuthority("ROLE_OWNER")

                .requestMatchers(HttpMethod.PUT, "/caveavin/bouteilles/**")
                .hasAuthority("ROLE_OWNER")

                .requestMatchers("/caveavin/regions/**")
                .hasAuthority("ROLE_OWNER")

                .requestMatchers("/caveavin/couleurs/**")
                .hasAuthority("ROLE_OWNER")

                // ===== fallback
                .anyRequest().denyAll()
        );

        http.csrf(csrf -> csrf.disable());

        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        return http.build();
    }
}
