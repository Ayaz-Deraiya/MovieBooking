package org.moviebooking.apigateway149;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtil {
    private static String SECRET_KEY ;

    @Value("${SECRET_KEY}")
    public void setSecret(String secret) {
        SECRET_KEY = secret;
    }
    public static Claims validateToken(String token) throws SignatureException {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody(); // returns claims if token is valid
    }
}

