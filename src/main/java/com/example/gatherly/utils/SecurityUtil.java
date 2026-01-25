package com.example.gatherly.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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
}
