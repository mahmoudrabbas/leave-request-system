package com.empSystem.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {
    public final SecretKey JWT_SECRET_KEY;
//    private final SecretKey jwtSecretKey;


    public JwtUtils(@Value("${jwt.secret.key}") String secret_key) {
        this.JWT_SECRET_KEY = Keys.hmacShaKeyFor(secret_key.getBytes());
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3))
                .signWith(this.JWT_SECRET_KEY, io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaimsFromToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(this.JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaimsFromToken(token);
        return resolver.apply(claims);
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }


    public boolean isTokenValid(UserDetails userDetails, String token) {
        return !isTokenExpired(token) && userDetails.getUsername().equals(extractUsername(token));
    }

}
