package com.example.gatherly.mappers;

import com.example.gatherly.dtos.EventAdditionalData;
import com.example.gatherly.dtos.EventDto;
import com.example.gatherly.entity.Event;
import com.example.gatherly.utils.ObjectMapperUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
   EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

   @Mapping(source = "eventDescription",target = "description")
   @Mapping(target = "location", source = "eventLocation")
   @Mapping(target = "noOfParticipants", source = "ticketsAvailable")
   EventDto eventToEventDto(Event event);

   @Mapping(source = "description",target = "eventDescription")
   @Mapping(target = "id",ignore = true)
   @Mapping(source = "location", target = "eventLocation")
   @Mapping(source = "noOfParticipants", target = "ticketsAvailable")
   void eventDtoToEvent(EventDto eventDto, @MappingTarget Event event);


   void updateEventFromAdditionalData(EventDto eventDto, @MappingTarget EventAdditionalData eventAdditionalData);


   void updateEventDtoFromAdditionalData(@MappingTarget EventDto eventDto,  EventAdditionalData eventAdditionalData);

   @AfterMapping
   default void enrichWithAdditionalData(
      Event event,
      @MappingTarget EventDto eventDto
   ) {
      if (event.getAdditionalData() == null) {
         return;
      }

      EventAdditionalData additionalData =
         ObjectMapperUtils.convertJsonToObjectWithDefault(
            event.getAdditionalData(),
            EventAdditionalData.class
         );

      updateEventDtoFromAdditionalData(eventDto, additionalData);

   }
}
