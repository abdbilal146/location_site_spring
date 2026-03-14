package com.rent.mancer.services;


import com.rent.mancer.models.MonthlyRevenue;
import com.rent.mancer.repository.MonthlyRevenueRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MonthlyRevenueService {


    @Autowired
    private MonthlyRevenueRepository monthlyRevenueRepository;

    @Transactional
    public MonthlyRevenue addRevenue(BigDecimal price) {
        LocalDateTime now = LocalDateTime.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();


        Optional<MonthlyRevenue> lastEntryOpt = monthlyRevenueRepository.findFirstByOrderByIdDesc();

        MonthlyRevenue targetRevenue;

        // Vérifier si on doit créer une nouvelle ligne (si vide ou si mois/année différents)
        if (lastEntryOpt.isEmpty() ||
                lastEntryOpt.get().getMonth() != currentMonth ||
                lastEntryOpt.get().getYear() != currentYear) {

            // Nouveau mois ou premier enregistrement : on initialise une nouvelle ligne
            targetRevenue = new MonthlyRevenue();
            targetRevenue.setYear(currentYear);
            targetRevenue.setMonth(currentMonth);
            targetRevenue.setTotalRevenue(BigDecimal.ZERO);
            targetRevenue.setTotalReservations(0);
        } else {
            // Même mois : on récupère la ligne existante
            targetRevenue = lastEntryOpt.get();
        }


        targetRevenue.setTotalRevenue(targetRevenue.getTotalRevenue().add(price));
        targetRevenue.setTotalReservations(targetRevenue.getTotalReservations() + 1);
        targetRevenue.setGeneratedAt(now);

        calculateGrowth(targetRevenue);


        return monthlyRevenueRepository.save(targetRevenue);
    }


    public MonthlyRevenue getLastMonthRevenue(){
        return monthlyRevenueRepository.findFirstByOrderByIdDesc().orElseGet(
                () -> {
                    MonthlyRevenue defaultRevenue = new MonthlyRevenue();
                    defaultRevenue.setTotalRevenue(BigDecimal.ZERO);
                    defaultRevenue.setTotalReservations(0);
                    defaultRevenue.setRevenueGrowthPercentage(0.0);
                    defaultRevenue.setMonth(LocalDateTime.now().getMonthValue());
                    defaultRevenue.setYear(LocalDateTime.now().getYear());
                    return defaultRevenue;
                }
        );
    }




    // logique metier


    private void calculateGrowth(MonthlyRevenue current) {
        // On cherche le mois précédent en base
        // Si current est nouveau, le "previous" est le dernier en base
        // Si on est en train de mettre à jour le mois actuel, le "previous" est celui d'avant l'actuel

        List<MonthlyRevenue> allRevenues = monthlyRevenueRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        // On prend le premier élément qui n'est pas notre "current"
        Optional<MonthlyRevenue> previousMonthOpt = allRevenues.stream()
                .filter(r -> !r.getId().equals(current.getId()))
                .findFirst();

        if (previousMonthOpt.isPresent() && previousMonthOpt.get().getTotalRevenue().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal previousTotal = previousMonthOpt.get().getTotalRevenue();
            BigDecimal currentTotal = current.getTotalRevenue();

            // Formule : ((Actuel - Précédent) / Précédent) * 100
            BigDecimal difference = currentTotal.subtract(previousTotal);
            BigDecimal growth = difference.divide(previousTotal, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));

            current.setRevenueGrowthPercentage(growth.doubleValue());
        } else {

            current.setRevenueGrowthPercentage(0.0);
        }
    }
}
