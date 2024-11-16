package com.vishwa.twitter.Services;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    private String encodedKey = "";

    public String generateKey(String userId){
        Map<String,Object> claims = new HashMap<>();
        try {
            return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userId)
                // .issuedAt(new Date(System.currentTimeMillis()))
                // .expiration(new Date(System.currentTimeMillis()+60*60*24))
                .and()
                .signWith(getKey())
                .compact();
            
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private Key getKey() throws NoSuchAlgorithmException{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey = keyGenerator.generateKey();
        encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        byte[] keyBytes = Decoders.BASE64.decode(encodedKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
