package com.example.gatherly.entity;

import com.example.gatherly.enums.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "roles_and_authorities")
@Getter
public class RolesAndAuthorities {
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id
   private  Long id;
   @Enumerated(EnumType.STRING)
   private Roles role;
   private String authority;
}
