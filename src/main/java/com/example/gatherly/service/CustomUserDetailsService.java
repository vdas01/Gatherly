package com.example.gatherly.service;

import com.example.gatherly.entity.User;
import com.example.gatherly.enums.UserStatus;
import com.example.gatherly.exception.ProgramException;
import com.example.gatherly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
   private final UserRepository userRepository;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findByUserName(username)
         .orElseThrow(() -> new ProgramException("NO user found with username: " + username));

      return org.springframework.security.core.userdetails.User.builder()
         .username(user.getUserName())
         .password(user.getPassword()) // hashed
         .authorities("ROLE") // DB roles
         .accountLocked(!UserStatus.ACTIVE.equals(user.getUserStatus()))
         .build();
   }
}
