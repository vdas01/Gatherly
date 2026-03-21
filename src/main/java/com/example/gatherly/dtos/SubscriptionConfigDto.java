package com.example.gatherly.dtos;

import com.example.gatherly.enums.EventStatus;
import com.example.gatherly.enums.SubscriptionType;
import com.example.gatherly.interfaces.AbstractFeatureConfig;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class SubscriptionConfigDto implements AbstractFeatureConfig {
   private Long id;
   private String subscriptionName;
   private String tagline;
   private SubscriptionType subscriptionType;
   private String description;
   private BigDecimal price;
   private BigDecimal discountPrice;
   private LocalDateTime startTime;
   private LocalDateTime endTime;
   private EventStatus subscriptionStatus;
}
