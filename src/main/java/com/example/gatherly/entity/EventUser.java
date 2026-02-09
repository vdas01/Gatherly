package com.example.gatherly.entity;

import com.example.gatherly.enums.EventStatus;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;

@Entity(name = "event_users")
@Table(
   name = "event_users",
   uniqueConstraints = {
      @UniqueConstraint(columnNames = {"user_id", "event_id"})
   }
)
@Getter
public class EventUser {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "event_id", nullable = false)
   private Event event;


   @Enumerated(EnumType.STRING)
   private EventStatus status;

   @Column(name = "email_retry_count")
   private Integer emailRetryCount = 0;

}
