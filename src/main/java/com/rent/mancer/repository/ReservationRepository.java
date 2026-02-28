package com.rent.mancer.repository;

import com.rent.mancer.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByCarId(Long carId);
}
