package com.paypal.userService.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

    private static final String SECRET = "";

    private Key getSigninKey(){
       return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateToken(String token,String userName){
        try {
            extractEmail(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    public String extractUserName(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public String extractRole(String token){
        return (String) Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }
    public Long extractUserId(String token){
        return Long.parseLong( Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId").toString()
        );
    }

    public String generateToken(Long userId,String email,String role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId",userId);
        claims.put("role",role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
