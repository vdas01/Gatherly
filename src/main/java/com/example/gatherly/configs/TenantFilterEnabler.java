package com.example.gatherly.configs;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantFilterEnabler {

   private final EntityManager entityManager;

   public void enable() {
      Session session = entityManager.unwrap(Session.class);
      session.enableFilter("locationFilter")
         .setParameter("locationId", TenantContext.getLocation());
   }
}

