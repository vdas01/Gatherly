package com.example.gatherly.repository;

import com.example.gatherly.entity.RolesAndAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleAuthorityRepository extends JpaRepository<RolesAndAuthorities, Long> {

   List<RolesAndAuthorities> findByRole(String role);
}
