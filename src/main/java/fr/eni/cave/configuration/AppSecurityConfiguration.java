package fr.eni.cave.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class AppSecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AppSecurityConfiguration.class);

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT login, password, 1 FROM cav_user WHERE login = ?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT login, authority FROM cav_user WHERE login = ?");

        return jdbcUserDetailsManager;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {
        http.authorizeHttpRequests(auth -> auth
                // ===== VISITEUR (public)
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

        http.httpBasic(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
