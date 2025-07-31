package com.paola.foro.security;

import com.paola.foro.alumno.Alumno;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String secret;

    public String generarToken(Alumno alumno) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + 86400000); // 24 horas

        return Jwts.builder()
                .setIssuer("foro API")
                .setSubject(alumno.getEmail())
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(getClave(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getClave() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getClave())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
