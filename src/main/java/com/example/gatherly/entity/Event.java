package com.example.gatherly.entity;

import com.example.gatherly.enums.EventStatus;
import com.example.gatherly.enums.EventType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "event")
@Getter
@Setter
public class Event extends TenantBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "event_description")
    private String eventDescription;
    @Column(name = "event_start_date")
    private LocalDateTime eventStartDate;
    @Column(name = "event_location")
    private String eventLocation;
    @Column(name = "event_end_date")
    private LocalDateTime eventEndDate;
    @Column(name = "event_price")
    private BigDecimal eventPrice;
    @Column(name = "event_status")
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;
    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    @Column(name = "tickets_available")
    private Integer ticketsAvailable;
    @Column(name = "tickets_booked")
    private Integer ticketsBooked = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();
   @OneToMany(mappedBy = "event")
   private Set<EventUser> usersParticipated = new HashSet<>();

   /**
    * @see {@link com.example.gatherly.dtos.EventAdditionalData}
    */
   @Column(name = "additional_data")
   private String additionalData;
}
