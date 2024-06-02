package com.G23.ParkIt.util;

import com.G23.ParkIt.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // Obtain the secret and expiration from application properties
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Generates a JWT for the given user.
     *
     * @param user the user for whom the token will be generated
     * @return the JWT token
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Extracts the username from the given JWT.
     *
     * @param token the JWT token
     * @return the username embedded in the token
     */
    public String extractUsername(String token) {
        return (String) getClaims(token).get("username");
    }

    /**
     * Validates the given JWT against the provided user.
     *
     * @param token the JWT token
     * @param user  the user against whom the token will be validated
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token, User user) {
        try {
            Claims claims = getClaims(token);
            String username = claims.getSubject();
            Date expiration = claims.getExpiration();
            Date now = new Date();
            return username.equals(user.getUsername()) && now.before(expiration);
        } catch (Exception e) {
            // This can catch various token-based exceptions e.g. expired, tampered with, etc.
            return false;
        }
    }

    /**
     * Retrieves the claims from a given JWT.
     *
     * @param token the JWT token
     * @return the claims embedded in the token
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
