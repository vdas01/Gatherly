package com.example.gatherly.dtos;

import com.example.gatherly.enums.SubscriptionType;
import com.example.gatherly.interfaces.AbstractFeatureConfig;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SubscriptionConfigDto implements AbstractFeatureConfig {
   private String subscriptionName;
   private String tagline;
   private SubscriptionType subscriptionType;
   private String description;
   private BigDecimal price;
}
