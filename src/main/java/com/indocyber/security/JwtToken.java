package com.indocyber.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtToken {

    @Value("${secret.key}")
    private String SECRET_KEY;

    @Value("${audience}")
    private String audience;

    private Claims getClaims(String token){

        JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY);
        Jws<Claims> signatureAndClaims = parser.parseClaimsJws(token);

        return signatureAndClaims.getBody();
    }

    public String getUsername(String token) {

        Claims claims = getClaims(token);

        return claims.get("username", String.class);
    }

    public String generateToken(
            String subject,
            String username,
            String secretKey,
            String role,
            String audience) {


        JwtBuilder builder = Jwts.builder();
        builder = builder.setSubject(subject)
                .claim("username", username)
                .claim("role", role)
                .setIssuer("http://localhost:8080")
                .setAudience(audience)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 60000))
                .signWith(SignatureAlgorithm.HS256, secretKey);

        return builder.compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {

        Claims claims = getClaims(token);
        String user = claims.get("username", String.class);
        String audience = claims.getAudience();

        return (user.equals(userDetails.getUsername()) && this.audience.equals(audience));
    }
}
