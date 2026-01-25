package com.example.gatherly.dtos;

import com.example.gatherly.enums.EventStatus;
import com.example.gatherly.enums.EventType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventDto {
   private Long id;
   private String eventName;
   private String description;
   private String location;
   private String imageUrl;
   private LocalDateTime eventStartDate;
   private LocalDateTime eventEndDate;
   private BigDecimal eventPrice;
   private EventStatus eventStatus;
   private EventType eventType;
}
