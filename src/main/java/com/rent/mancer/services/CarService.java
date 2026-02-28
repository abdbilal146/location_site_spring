package com.rent.mancer.services;

import com.rent.mancer.models.Car;
import com.rent.mancer.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;


    public List<Car> getAllCars(){
        return carRepository.findAll();
    }


    public Car addNewCarAdmin(Car car, UUID uid){
        Car newCar = new Car();
        newCar.setUserUid(uid);
        newCar.setImageUrl(car.getImageUrl());
        newCar.setCategory(car.getCategory());
        newCar.setRegistrationNumber(car.getRegistrationNumber());
        newCar.setBrand(car.getBrand());
        newCar.setModel(car.getModel());
        newCar.setTransmission(car.getTransmission());
        newCar.setFuel(car.getFuel());
        newCar.setNumberOfSeats(car.getNumberOfSeats());
        newCar.setRentPrice(car.getRentPrice());
        newCar.setStatus(car.getStatus());

        return  carRepository.save(newCar);

    }

    public void deleteCar(
            Long id
    ){
        carRepository.deleteById(id);
    }


}
