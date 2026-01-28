package com.example.gatherly.service;

import com.example.gatherly.dtos.SubscriptionConfigDto;
import com.example.gatherly.entity.FeatureConfig;
import com.example.gatherly.entity.Subscription;
import com.example.gatherly.enums.FeatureName;
import com.example.gatherly.enums.SubscriptionStatus;
import com.example.gatherly.enums.SubscriptionType;
import com.example.gatherly.exception.ProgramException;
import com.example.gatherly.repository.FeatureConfigRepository;
import com.example.gatherly.repository.SubscriptionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
   private final ObjectMapper objectMapper;
   private final FeatureConfigRepository featureConfigRepository;
   private final SubscriptionRepository subscriptionRepository;

   public void registerSubscription(Long userId, SubscriptionType subscriptionType) throws JsonProcessingException {
      boolean isExists = subscriptionRepository.existsByUserIdAndSubscriptionStatus(userId, SubscriptionStatus.ACTIVE);
      if(Boolean.FALSE.equals(isExists)) {
         throw new ProgramException("Subscription already exists for userId: " + userId);
      }
      FeatureConfig featureConfig = featureConfigRepository.findByFeatureName(FeatureName.SUBSCRIPTION_DATA)
         .orElseThrow(() -> new RuntimeException("Feature config not found for feature name: " + FeatureName.SUBSCRIPTION_DATA));
      List<SubscriptionConfigDto> subscriptionConfigDtoList = objectMapper.readValue(featureConfig.getConfig(),new TypeReference<List<SubscriptionConfigDto>>() {});
     SubscriptionConfigDto subscriptionConfigDto =  subscriptionConfigDtoList.stream().filter(dto-> dto.getSubscriptionType().equals(subscriptionType.name()))
         .findFirst().orElseThrow(() -> new ProgramException(HttpStatus.NOT_FOUND,"Subscription config not found for subscription type: " + subscriptionType.name()));
      Subscription subscription = new Subscription();
      subscription.setSubscriptionType(subscriptionType);
      subscription.setSubscriptionPrice(subscriptionConfigDto.getPrice());
      subscription.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
      subscription.setSubscriptionEndTime(LocalDateTime.now().plusMonths(SubscriptionType.getValidityPeriod(subscriptionType)));
//      subscription.setUser(userId);
      subscriptionRepository.saveAndFlush(subscription);
   }


}
