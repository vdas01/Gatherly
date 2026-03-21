package com.example.gatherly.service;

import com.example.gatherly.dtos.EventAdditionalData;
import com.example.gatherly.dtos.EventDto;
import com.example.gatherly.dtos.PageResponse;
import com.example.gatherly.entity.Event;
import com.example.gatherly.entity.Ticket;
import com.example.gatherly.entity.User;
import com.example.gatherly.exception.ProgramException;
import com.example.gatherly.mappers.EventMapper;
import com.example.gatherly.repository.EventRepository;
import com.example.gatherly.repository.TicketRepository;
import com.example.gatherly.repository.UserRepository;
import com.example.gatherly.utils.ObjectMapperUtils;
import com.example.gatherly.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
   private final EventRepository eventRepository;
   private final UserRepository userRepository;
   private final TicketRepository ticketRepository;
   private final JavaMailSender mailSender;
   private final SpringTemplateEngine springTemplateEngine;

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
     return EventMapper.INSTANCE.eventToEventDto(eventRepository.save(event));
   }

   public EventDto getEventById(Long id) {
      Event event =  eventRepository.findById(id)
         .orElseThrow(() -> new ProgramException(HttpStatus.NOT_FOUND,"No event found for id," + id));
      EventAdditionalData additionalData =  ObjectMapperUtils.convertJsonToObjectWithDefault(event.getAdditionalData(), EventAdditionalData.class);
      EventDto eventDto = EventMapper.INSTANCE.eventToEventDto(event);
      eventDto.setDiscountPrice(additionalData.getDiscountPrice());
      return eventDto;
   }

   @Transactional
   public String registerEvent(Long eventId, Integer noOfTickets) {
      log.info("Register event with id {}, noOfTickets {}", eventId, noOfTickets);
      String username = SecurityUtil.getUserName();
      Event event = eventRepository.findById(eventId).
         orElseThrow(() -> new ProgramException("No event found for id," + eventId));
      User user = userRepository.findByUserName(username).
         orElseThrow(() -> new ProgramException("Username not found"));
      Ticket ticket = new Ticket();
      ticket.setTicketPrice(event.getEventPrice());
      ticket.setQuantity(noOfTickets);
      ticket.setUser(user);
      ticket.setEvent(event);
      ticketRepository.save(ticket);
      return "Event registered successfull";
   }

//   public String sendMail(String to,String subject,String m,String from) throws MessagingException {
//      Context context = new Context();
//      context.setVariable("mailDto", new MailDto(to,subject,m,from));
//      String content = springTemplateEngine.process("RemainderMail.html", context);
//      MimeMessage message = mailSender.createMimeMessage();
//      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//      helper.setFrom(from);
//      helper.setTo(to);
//      helper.setSubject(subject);
//      helper.setText(content, true);
//      try{
//         mailSender.send(message);
//      }catch (Exception e){
//         log.error(e.getMessage());
//         throw new ProgramException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to send mail, " + e.getMessage());
//      }
//     return "Mail sent successfully";
//   }

}
