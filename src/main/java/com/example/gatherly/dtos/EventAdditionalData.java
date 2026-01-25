package com.example.gatherly.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class EventAdditionalData {
   private Long noOfParticipants;
   private String imageUrl;
}
