package com.rent.mancer.models;


import com.rent.mancer.models.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.rmi.server.UID;
import java.util.UUID;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uid;
    private String name;
    private String familyName;
    private String email;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

}
