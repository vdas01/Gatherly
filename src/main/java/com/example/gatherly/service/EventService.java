package com.example.gatherly.service;

import com.example.gatherly.dtos.EventAdditionalData;
import com.example.gatherly.dtos.EventDto;
import com.example.gatherly.dtos.PageResponse;
import com.example.gatherly.entity.Event;
import com.example.gatherly.entity.User;
import com.example.gatherly.exception.ProgramException;
import com.example.gatherly.mappers.EventMapper;
import com.example.gatherly.repository.EventRepository;
import com.example.gatherly.repository.UserRepository;
import com.example.gatherly.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventService {
   private final EventRepository eventRepository;
   private final UserRepository userRepository;

   public PageResponse<EventDto> getAllEvents(Pageable pageable) {
      //keep a window of past 10 days and ahead of 15 days events
      LocalDateTime start = LocalDateTime.now().minusDays(10);
      LocalDateTime end = LocalDateTime.now().plusDays(15);
      Page<Event> eventPage =  eventRepository.findByEventEndDateBetween(
         start,
         end,
         pageable
      );
      return new PageResponse<>(
         eventPage.getContent().stream()
            .map(EventMapper.INSTANCE::eventToEventDto)
            .toList(),
            eventPage.getNumber(),
         eventPage.getSize(),
         eventPage.getTotalElements(),
         eventPage.getTotalPages(),
         eventPage.isLast()
      );
   }

   public EventDto createEvent(EventDto eventDto) {
      Event event = new Event();
      if(Objects.nonNull(eventDto.getId())) {
         event = eventRepository.findById(eventDto.getId()).orElseThrow(() -> new ProgramException(HttpStatus.NOT_FOUND,"No event found for id," + eventDto.getId()));
      }
      User user = userRepository.findByUserName("vishal123").orElse(null);
      EventAdditionalData additionalData = ObjectMapperUtils.convertJsonToObjectWithDefault(event.getAdditionalData(), EventAdditionalData.class);
      EventMapper.INSTANCE.updateEventFromAdditionalData(eventDto, additionalData);
      EventMapper.INSTANCE.eventDtoToEvent(eventDto, event);
      event.setAdditionalData(ObjectMapperUtils.convertDtoToJson(additionalData));
      event.setUser(user);
     return EventMapper.INSTANCE.eventToEventDto(eventRepository.saveAndFlush(event));
   }

   public EventDto getEventById(Long id) {
      return eventRepository.findById(id)
         .map(EventMapper.INSTANCE::eventToEventDto)
         .orElseThrow(() -> new ProgramException("No event found for id," + id));
   }

}
