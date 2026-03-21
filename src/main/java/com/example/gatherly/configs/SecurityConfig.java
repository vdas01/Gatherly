package com.example.gatherly.configs;

import com.example.gatherly.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
   private final AuthenticationConfiguration authenticationConfiguration;
   private final JwtAuthFilter jwtAuthFilter;
   private final JwtUtil jwtUtil;
   private final LocationFilter locationFilter;

   @Bean
   SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
         .cors(Customizer.withDefaults())
         .csrf(AbstractHttpConfigurer::disable)
         .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers("/api/user/login","/api/user/refresh","/api/event/mail").permitAll()
//            .requestMatchers(HttpMethod.POST, "/api/user").permitAll()
            .anyRequest().authenticated()
         )
         .sessionManagement(sess ->
            sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         )
         .exceptionHandling(ex -> ex
            .authenticationEntryPoint((request, response, authException) -> {
               response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
               response.setContentType("application/json");
               response.getWriter().write(
                  "{\"error\": \"" + authException.getMessage() + "\"}"
               );
            })
            .accessDeniedHandler((request, response, accessDeniedException) -> {
               response.setStatus(HttpServletResponse.SC_FORBIDDEN);
               response.setContentType("application/json");
               response.getWriter().write(
                  "{\"error\": \"" + accessDeniedException.getMessage() + "\"}"
               );
            })
         )
         .oauth2Login(oauth -> oauth
            .successHandler((request, response, authentication) -> {

               OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

               String email = oAuth2User.getAttribute("email");

               String token = jwtUtil.generateAccessToken(email);


               response.sendRedirect("http://localhost:5173/oauth-success?token=" + token);
            })
         )
         .formLogin(AbstractHttpConfigurer::disable);


      http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
      http.addFilterAfter(locationFilter, JwtAuthFilter.class);

      return http.build();
   }


   @Bean
   CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowedOrigins(List.of("http://localhost:5173"));
      config.setAllowedMethods(List.of(
         "GET", "POST", "PUT", "DELETE", "OPTIONS"
      ));
      config.setAllowedHeaders(List.of("*"));
      config.setAllowCredentials(true);

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", config);

      return source;
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager() throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
   }
}

