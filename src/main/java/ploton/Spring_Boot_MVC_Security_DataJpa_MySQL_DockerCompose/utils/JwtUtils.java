package ploton.Spring_Boot_MVC_Security_DataJpa_MySQL_DockerCompose.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtUtils {
    @Value("${jwt.secret}")
    private static String SECRET;
    @Value("${jwt.lifetime}")
    private static Long LIFE_TIME;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        String username = userDetails.getUsername();
        List<String> authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        claims.put(username, authorities);

        Date issueDate = new Date();
        Date expiredDate = new Date(issueDate.getTime() + LIFE_TIME);

        return null;
    }
}
