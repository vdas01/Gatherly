package com.example.gatherly.entity;

import com.example.gatherly.enums.SubscriptionStatus;
import com.example.gatherly.enums.SubscriptionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Subscription extends TenantBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(name = "subscription_name")
//    private String subscriptionName;
//    @Column(name = "subscription_description")
//    private String subscriptionDescription;
    @Column(name = "subscription_price")
    private BigDecimal subscriptionPrice;
    @Column(name = "subscription_type")
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;
    @Column(name = "subscription_status")
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;
    @Column(name = "subscription_end_time")
    private LocalDateTime subscriptionEndTime;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
