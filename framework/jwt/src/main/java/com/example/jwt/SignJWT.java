package com.example.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * @author simple
 */
public class SignJWT {
    public static void main(String[] args) {
        String apiKeyId = "app_51e8fc06aa97va00eb2bc689";

        String apiKeySecret = "V1lmzJFCvzCRfXls9kEEhk3_0pklbZgW6R0cVvhRUpIUkBRu4gxfByZVAfzA-nSN-RvSoIoUUKW8HhcE4ZIYxw";

        String payload = """
            {
              "scope": "app",
              "exp": 1755779039
            }
            """;

        byte[] keyBytes = apiKeySecret.getBytes(StandardCharsets.UTF_8);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        String jwt = Jwts.builder()
            .setHeaderParam("kid", apiKeyId)
            .setPayload(payload)
            .signWith(key)
            .compact();
        System.out.println(jwt);
    }
}
