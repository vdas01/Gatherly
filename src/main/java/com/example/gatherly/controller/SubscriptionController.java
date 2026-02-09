package com.example.gatherly.controller;

import com.example.gatherly.enums.SubscriptionType;
import com.example.gatherly.service.SubscriptionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscription")
public class SubscriptionController {
   private final SubscriptionService subscriptionService;

   @PostMapping
   public ResponseEntity<String> subscribe(@RequestParam SubscriptionType subscriptionType) throws JsonProcessingException {
      return ResponseEntity.ok(subscriptionService.registerSubscription(subscriptionType));
   }

}
