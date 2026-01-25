package com.example.gatherly.repository;

import com.example.gatherly.entity.Subscription;
import com.example.gatherly.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

   boolean existsByUserIdAndSubscriptionStatus(Long userId, SubscriptionStatus subscriptionStatus);
}
