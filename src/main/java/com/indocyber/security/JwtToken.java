package com.indocyber.security;

import com.indocyber.entity.Account;
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

    private final int SESSION_TIME = 60000 * 10;

    public Claims getClaims(String token){

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
            String role,
            String audience) {


        JwtBuilder builder = Jwts.builder();
        builder = builder
                .setSubject(subject)
                .claim("username", username)
                .claim("role", role)
                .setIssuer("http://localhost:8080")
                .setAudience(audience)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + SESSION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY);

        return builder.compact();
    }

    public String generateRefreshToken(
            String subject,
            String username) {


        JwtBuilder builder = Jwts.builder();
        builder = builder
                .setSubject(subject)
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + (SESSION_TIME * 2)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY);

        return builder.compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {

        Claims claims = getClaims(token);
        String user = claims.get("username", String.class);
        String audience = claims.getAudience();

        return (user.equals(userDetails.getUsername()) && this.audience.equals(audience));
    }

    public Boolean validateRefreshToken(String refreshToken, Account account) {
        Claims claims = getClaims(refreshToken);
        String username = claims.get("username", String.class);
        Date expiration = claims.getExpiration();

        return (expiration.after(new Date()) && username.equals(account.getUsername()));
    }
}
