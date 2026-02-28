package com.rent.mancer.controllers;


import com.rent.mancer.models.Car;
import com.rent.mancer.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/cars")
public class CarController {

    @Autowired
    private CarService carService;


    @PostMapping("/add")
    public ResponseEntity<Car> addNewCar(
            @RequestBody Car car,
            @AuthenticationPrincipal Jwt jwt
            ){

        String sub = jwt.getSubject();
        UUID uid = UUID.fromString(sub);

        return ResponseEntity.ok(carService.addNewCarAdmin(car,uid));
    }


    @GetMapping
    public ResponseEntity<List<Car>> getAllCars(){
        return ResponseEntity.ok(carService.getAllCars());
    }


    @PatchMapping
    public ResponseEntity<Car> updateCar(
            @RequestBody Car car,
            @AuthenticationPrincipal Jwt jwt
    ){
        return  ResponseEntity.ok(new Car());
    }


    @DeleteMapping("{id}")
    public void deleteCar(
            @PathVariable Long id
    ){
        carService.deleteCar(id);
    }



}
