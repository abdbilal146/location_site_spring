package com.rent.mancer.controllers;


import com.rent.mancer.models.Reservation;
import com.rent.mancer.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;


    @PostMapping("/add/{clientId}/{carId}")
    public ResponseEntity<Reservation> addNewReservation(
            @RequestBody Reservation reservation,
            @PathVariable Long clientId,
            @PathVariable Long carId,
            @AuthenticationPrincipal Jwt jwt
            ){

        String sub = jwt.getSubject();
        UUID uuid = UUID.fromString(sub);

        return ResponseEntity.ok(
                reservationService.addReservation(
                        reservation,
                        clientId,
                        carId,
                        uuid
                )
        );
    }


    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations(){
        return ResponseEntity.ok(reservationService.getAllReservations());
    }
}
