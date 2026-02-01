package com.example.gatherly.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.example.gatherly.utils.Constants.ACCESS_EXP;
import static com.example.gatherly.utils.Constants.ACCESS_SECRET_KEY;
import static com.example.gatherly.utils.Constants.REFRESH_EXP;
import static com.example.gatherly.utils.Constants.REFRESH_SECRET_KEY;

@Component
public class JwtUtil {



   public String generateAccessToken(String username) {
      return Jwts.builder()
         .setSubject(username)
         .setIssuedAt(new Date())
         .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXP))
         .signWith(Keys.hmacShaKeyFor(ACCESS_SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
         .compact();
   }

   public String generateRefreshToken(String username) {
      return Jwts.builder()
         .setSubject(username)
         .setIssuedAt(new Date())
         .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXP))
         .signWith(Keys.hmacShaKeyFor(REFRESH_SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
         .compact();
   }

   public String extractUsernameFromRefresh(String token) {
      return Jwts.parserBuilder()
         .setSigningKey(REFRESH_SECRET_KEY.getBytes())
         .build()
         .parseClaimsJws(token)
         .getBody()
         .getSubject();
   }

   public boolean validateToken(String token) {
      try {
//         extractUsername(token);
         return true;
      } catch (Exception e) {
         return false;
      }
   }
}
