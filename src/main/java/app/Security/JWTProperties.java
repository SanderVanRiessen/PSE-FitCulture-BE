package app.Security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {
    private String passphrase;
    private String issuer;
    private long expiration;

    // Getters and Setters
    public String getPassphrase() { return passphrase; }
    public void setPassphrase(String passphrase) { this.passphrase = passphrase; }

    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }

    public long getExpiration() { return expiration; }
    public void setExpiration(long expiration) { this.expiration = expiration; }
}
