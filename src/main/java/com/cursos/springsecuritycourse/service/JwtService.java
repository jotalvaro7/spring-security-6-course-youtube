package com.cursos.springsecuritycourse.service;

import com.cursos.springsecuritycourse.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${security.jwt.expiration-minutes}")
    private long expirationMinutes;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public String generateToken(User user, Map<String, Object> extraClaims) {

        Date issueAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issueAt.getTime() + (expirationMinutes * 60 * 1000));


        return Jwts.builder()
                .claims()
                .add(extraClaims)
                .subject(user.getUsername())
                .issuedAt(issueAt)
                .expiration(expiration)
                .and()
                .signWith(getSignInKey())
                .compact();

    }

    private Key getSignInKey() {

        byte[] secretAsBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith((SecretKey) getSignInKey()).build()
                .parseSignedClaims(jwt).getPayload();
    }
}
