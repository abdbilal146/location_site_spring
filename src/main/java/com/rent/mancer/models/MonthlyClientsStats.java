package com.rent.mancer.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "monthly_clients_stats")
public class MonthlyClientsStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;

    private Integer month;

    private Long totalCumulativeClients;

    private Double growthPercentage;

    private LocalDateTime lasUpdate;
}
