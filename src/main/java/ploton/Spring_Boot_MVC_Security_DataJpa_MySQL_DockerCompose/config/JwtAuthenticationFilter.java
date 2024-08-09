package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.entity.Role;
import ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.utils.JwtUtils;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer")) {

            try {
                String token = header.split("Bearer ")[1];
                String username = jwtUtils.getUserName(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    List<SimpleGrantedAuthority> roles = jwtUtils.getRoles(token)
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();

                    log.warn("roles - " + roles);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            username, null, roles
                    );
                    log.warn("auth token - " + authenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    log.warn("Context Auth - " + SecurityContextHolder.getContext().getAuthentication());
                }

            } catch (ExpiredJwtException ex) {
                log.warn("Token expired life is over - " + ex.getMessage());
            } catch (SignatureException ex) {
                log.warn("Wrong token signature - " + ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
