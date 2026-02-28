package com.rent.mancer.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rent.mancer.models.records.Address;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Table(name = "clients")
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    @Embedded
    private Address address;

    private Boolean accountStatus;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Reservation> reservations;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private UUID createdBy;


}
