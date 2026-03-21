package com.example.gatherly.dtos;

import com.example.gatherly.enums.EventStatus;
import com.example.gatherly.enums.EventType;
import com.fasterxml.jackson.annotation.JsonFormat;
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
   @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
   private LocalDateTime eventStartDate;
   @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
   private LocalDateTime eventEndDate;
   private BigDecimal eventPrice;
   private BigDecimal discountPrice;
   private EventStatus eventStatus = EventStatus.AVAILABLE;
   private EventType eventType;
   private Long noOfParticipants;
}
