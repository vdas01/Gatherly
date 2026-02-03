package com.example.gatherly.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {

   private static final BCryptPasswordEncoder ENCODER =
      new BCryptPasswordEncoder();

   public static String encode(String rawPassword) {
      return ENCODER.encode(rawPassword);
   }

   public static boolean matches(String raw, String encoded) {
      return ENCODER.matches(raw, encoded);
   }

   public static String getUserName(){
      SecurityContext securityContext = SecurityContextHolder.getContext();
      Authentication authentication = securityContext.getAuthentication();
      if (authentication == null || !authentication.isAuthenticated()) {
         return null;
      }

      Object principal = authentication.getPrincipal();

      if (principal instanceof UserDetails userDetails) {
         return userDetails.getUsername();
      }

      return authentication.getName();
   }
}
