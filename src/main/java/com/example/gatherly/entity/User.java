package com.example.gatherly.entity;

import com.example.gatherly.enums.UserRole;
import com.example.gatherly.enums.UserStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class User extends TenantBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(name = "phone_number")
    private Long phoneNumber;
    @Column(name = "user_name", unique = true)
    private String userName;
    @Column
    private String bio;
    @Column
    private String profession;
    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.ACTIVE;

    @Column(name = "custom_roles_authorities")
    private String customRolesAuthorities;
   /**
    * @see {@link com.example.gatherly.dtos.UserAdditionalData}
    */
   @Column(name = "additional_data")
    private String additionalData;
   /** event hosted by user so in host_id column of each event will be saved. event has to saved this user **/
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();
    /** tickets brought by user. ticket has to save this user **/
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();
    /** Subscription taken by user. **/
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Subscription pass;

   @OneToMany(mappedBy = "user")
   private Set<EventUser> eventsParticipated = new HashSet<>();
}
