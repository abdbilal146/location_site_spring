package com.rent.mancer.repository;


import com.rent.mancer.models.MonthlyClientsStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyClientsStatsRepository extends JpaRepository<MonthlyClientsStats, Long> {
}
