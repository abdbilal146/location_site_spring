package com.rent.mancer.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "monthly_revenues")
public class MonthlyRevenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;

    private Integer month;

    @Column(nullable = false)
    private BigDecimal totalRevenue;

    private Double revenueGrowthPercentage;

    private Integer totalReservations;

    private LocalDateTime generatedAt;
}
