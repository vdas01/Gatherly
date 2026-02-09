package com.example.gatherly.service;

import com.example.gatherly.entity.User;
import com.example.gatherly.enums.Roles;
import com.example.gatherly.enums.UserStatus;
import com.example.gatherly.exception.ProgramException;
import com.example.gatherly.repository.RoleAuthorityRepository;
import com.example.gatherly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
   private final UserRepository userRepository;
   private final RoleAuthorityRepository roleAuthorityRepository;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findByUserName(username)
         .orElseThrow(() -> new ProgramException("NO user found with username: " + username));
      Set<String> authoritiesSet = new HashSet<>();
      List<Roles> rolesList = new ArrayList<>();

      Arrays.stream(user.getCustomRolesAuthorities().split(","))
         .map(String::trim)
         .forEach(value -> {
            if (value.startsWith("ROLE_")) {
               Roles role = EnumUtils.getEnum(Roles.class, value);
               if (role != null) {
                  rolesList.add(role);
                  authoritiesSet.add(role.name()); // important
               }
            } else {
               authoritiesSet.add(value);
            }
         });


      rolesList.forEach(role ->
         roleAuthorityRepository.findByRole(role.name())
            .forEach(entity -> authoritiesSet.add(entity.getAuthority()))
      );

      GrantedAuthority[] grantedAuthorities =
         authoritiesSet.stream()
            .map(SimpleGrantedAuthority::new)
            .toArray(GrantedAuthority[]::new);

      return org.springframework.security.core.userdetails.User.builder()
         .username(user.getUserName())
         .password(user.getPassword())
         .authorities(grantedAuthorities)
         .accountLocked(!UserStatus.ACTIVE.equals(user.getUserStatus()))
         .build();

   }
}
