package com.ciuslo.keycloakspringbootmicroservice.service;

import java.util.Base64;

public class JwtDecoder {
    public String decodeJwt(String bearerToken){ 
            bearerToken = bearerToken.replaceAll("Bearer ", "");       
            String[] chunks = bearerToken.split("\\."); // token.split("\\.");
            Base64.Decoder decoder = Base64.getDecoder();
            String payload = new String(decoder.decode(chunks[1]));
        return payload;
    }
}
