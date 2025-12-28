package org.moviebooking.apigateway.Security.Config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    public static String SECRET;

    @Value("${SECRET}")
    public void setSecret(String secret) {
        SECRET = secret;
    }
    SecretKey key= Keys.hmacShaKeyFor(SECRET.getBytes());

    private static final long Expiration_Time=2*Time.HOURS;


    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+Expiration_Time))
                .signWith(key)
                .compact();
    }


    public String generateToken(String username, Map<String,Object> claims) {
        return Jwts.builder()
                .claims()
                .add(claims)
                .and()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+Expiration_Time))
                .signWith(key)
                .compact();
    }


    public String getUsername(String token) {
        return getClaims(token,Claims::getSubject);
    }



    public boolean isExpired(String token) {
        return getClaims(token,Claims::getExpiration).before(new Date());
    }


    public boolean validateToken(String token, UserDetails user) {
        String username = getUsername(token);
        boolean isExpired = isExpired(token);
        boolean ans=!isExpired && username.equals(user.getUsername());
        if(!ans){
            System.out.println("Invalid token");
        }
        return ans;
    }


    private <T> T getClaims(String token, Function<Claims,T> claimsResolver) {
        Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public static class Time {
        public static final long SECONDS = 1000L;
        public static final long MINUTES = SECONDS * 60;
        public static final long HOURS = MINUTES * 60;
        public static final long DAYS = HOURS * 24;
        public static final long WEEK = DAYS * 7;
        public static final long MONTH = DAYS * 30;
        public static final long YEAR = DAYS * 365;
    }


}
