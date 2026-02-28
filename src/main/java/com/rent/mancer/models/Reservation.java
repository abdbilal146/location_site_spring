package com.rent.mancer.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rent.mancer.models.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID createdBy;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne()
    @JoinColumn(name = "car_id", unique = true)
    private Car car;

    private String startDate;

    private String endDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

}
