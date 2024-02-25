package app.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JWTToken {

    private final JWTProperties jwtProperties;
    private final Key key;

    @Autowired
    public JWTToken(JWTProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.getPassphrase().getBytes());
    }

    public Claims validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return claimsJws.getBody();
        } catch (Exception e) {
            throw new RuntimeException("JWT token validation failed", e);
        }
    }

    public String generateToken(Authentication authentication) {
        String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtProperties.getExpiration());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }
}
