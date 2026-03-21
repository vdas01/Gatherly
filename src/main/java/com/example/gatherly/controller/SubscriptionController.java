package com.example.gatherly.controller;

import com.example.gatherly.dtos.SubscriptionConfigDto;
import com.example.gatherly.enums.SubscriptionType;
import com.example.gatherly.service.SubscriptionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscription")
public class SubscriptionController {
   private final SubscriptionService subscriptionService;

   @PostMapping
   public ResponseEntity<String> subscribe(@RequestParam SubscriptionType subscriptionType) throws JsonProcessingException {
      return ResponseEntity.ok(subscriptionService.registerSubscription(subscriptionType));
   }

   @GetMapping
   public List<SubscriptionConfigDto> getSubscriptionList() throws JsonProcessingException {
      return subscriptionService.getSubscriptionList();
   }

   @GetMapping("/{id}")
   public SubscriptionConfigDto getSubscriptionById(@PathVariable Long id) throws JsonProcessingException {
      return subscriptionService.getSubscriptionById(id);
   }

}
