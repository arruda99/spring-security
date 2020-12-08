package com.springsecurity.service;

import com.springsecurity.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


import java.util.Date;

@Service
public class TokenService {

    @Value("{app.jwt.expiration}")
    private Long expiration;

    @Value("{app.jwt.secretKey}")
    private String secretKey;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date expirationDate = new Date(new Date().getTime() + expiration);
        return Jwts.builder().setIssuer("Spring Security").setSubject(user.getId().toString())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
