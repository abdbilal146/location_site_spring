package com.rent.mancer.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID userUid;

    private String imageUrl;

    private String category;

    private String registrationNumber;

    private String brand;

    private String model;

    private String transmission;

    private String fuel;

    private int numberOfSeats;

    private BigDecimal rentPrice = new BigDecimal("0");

    private Boolean status;

}
