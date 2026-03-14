package com.rent.mancer.services;


import com.rent.mancer.models.Car;
import com.rent.mancer.models.Client;
import com.rent.mancer.models.Reservation;
import com.rent.mancer.repository.CarRepository;
import com.rent.mancer.repository.ClientRepository;
import com.rent.mancer.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationService {


    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private MonthlyRevenueService monthlyRevenueService;

    @Transactional
    public Reservation addReservation(
            Reservation reservation,
            Long clientId,
            Long carId,
            UUID uuid
    ){

        if (reservationRepository.existsByCarId(carId)) {
            throw new IllegalStateException("Cette voiture est déjà réservée.");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));


        LocalDate start = LocalDate.parse(reservation.getStartDate());
        LocalDate end = LocalDate.parse(reservation.getEndDate());
        long days = ChronoUnit.DAYS.between(start, end);
        if (days <= 0) days = 1;
        BigDecimal totalPrice = car.getRentPrice().multiply(BigDecimal.valueOf(days));


        Reservation newReservation = new Reservation();
        newReservation.setCreatedBy(uuid);
        newReservation.setClient(client);
        newReservation.setCar(car);
        newReservation.setPrice(totalPrice);
        newReservation.setStartDate(reservation.getStartDate());
        newReservation.setEndDate(reservation.getEndDate());
        newReservation.setCreatedAt(LocalDateTime.now());
        newReservation.setReservationStatus(reservation.getReservationStatus());

        monthlyRevenueService.addRevenue(totalPrice);

        return reservationRepository.save(newReservation);
    }


    // this method to get all reservations

    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }
}
