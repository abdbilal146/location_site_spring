package com.rent.mancer.models.records;


import jakarta.persistence.Embeddable;

@Embeddable
public record Address(
        String address,
        String city,
        String postalCode

) {
}
