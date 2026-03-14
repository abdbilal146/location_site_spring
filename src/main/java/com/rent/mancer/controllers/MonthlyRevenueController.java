package com.rent.mancer.controllers;


import com.rent.mancer.models.MonthlyRevenue;
import com.rent.mancer.services.MonthlyRevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/revenue")
public class MonthlyRevenueController {


    @Autowired
    private MonthlyRevenueService monthlyRevenueService;


    @GetMapping("/monthly")
    public ResponseEntity<MonthlyRevenue> getMonthlyRevenue(){
        return ResponseEntity.ok(monthlyRevenueService.getLastMonthRevenue());
    }
}
