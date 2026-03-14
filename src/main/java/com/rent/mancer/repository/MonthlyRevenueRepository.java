package com.rent.mancer.repository;

import com.rent.mancer.models.MonthlyRevenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MonthlyRevenueRepository extends JpaRepository<MonthlyRevenue, Long> {

    Optional<MonthlyRevenue> findFirstByOrderByIdDesc();
}
