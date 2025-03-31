package com.s13sh.jwt.security.jwt;
import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretKeyGenerator {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32];
        secureRandom.nextBytes(keyBytes);

        String base64EncodedKey = Base64.getEncoder().encodeToString(keyBytes);

        System.out.println("Generated JWT Secret Key: " + base64EncodedKey);
    }
}