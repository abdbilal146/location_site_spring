package com.rent.mancer.services;


import com.rent.mancer.models.Car;
import com.rent.mancer.models.Client;
import com.rent.mancer.models.Reservation;
import com.rent.mancer.repository.CarRepository;
import com.rent.mancer.repository.ClientRepository;
import com.rent.mancer.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Reservation addReservation(
            Reservation reservation,
            Long clientId,
            Long carId,
            UUID uuid
    ){

        boolean carAlreadyReserved = reservationRepository.existsByCarId(carId);

        if (carAlreadyReserved) {
            throw new IllegalStateException("Cette voiture est déjà réservée.");
        }

        //find the right client
        Client client = clientRepository.findById(clientId).orElseThrow(
                ()-> new EntityNotFoundException("Client not found")
        );

        // find right Car
        Car car = carRepository.findById(carId).orElseThrow(
                ()-> new EntityNotFoundException("Car not found")
        );


        Reservation newReservation = new Reservation();
        newReservation.setCreatedBy(uuid);
        newReservation.setClient(client);
        newReservation.setCar(car);
        newReservation.setStartDate(reservation.getStartDate());
        newReservation.setEndDate(reservation.getEndDate());
        newReservation.setCreatedAt(LocalDateTime.now());
        newReservation.setReservationStatus(reservation.getReservationStatus());

        return reservationRepository.save(newReservation);
    }


    // this method to get all reservations

    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }
}
