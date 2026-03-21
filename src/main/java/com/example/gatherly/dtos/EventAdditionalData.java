package com.example.gatherly.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class EventAdditionalData {
   private String imageUrl;
   private BigDecimal discountPrice;
   private Long noOfParticipants;
}
