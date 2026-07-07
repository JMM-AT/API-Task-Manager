package com.TaskManagement.Application.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private  String SECRET_KEY ;

    public String generateToken(@NotBlank(message = "username is empty") String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .signWith(getSign())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .compact();
    }
    public SecretKey getSign(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
    public Claims getAllClaims(String token){
        return Jwts.parser().verifyWith(getSign()).build().parseSignedClaims(token).getPayload();
    }
    public String extractUserName(String token){
        return getAllClaims(token).getSubject();
    }
    public boolean isTokenExpired(String token){
        return getAllClaims(token).getExpiration().before(new Date());
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        String username=extractUserName(token);
        return username.equals(userDetails.getUsername())&& !isTokenExpired(token);
    }


}
