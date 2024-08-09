package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.config;

import jakarta.servlet.FilterChain;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.
                authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/users/reg", "/api/v1/users/login").permitAll()
                        .requestMatchers("/api/v1/users/id/").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers("/api/v1/users/add-role").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers("/api/v1/users/profile").authenticated()
                        .anyRequest().authenticated())
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .build();
    }
}
