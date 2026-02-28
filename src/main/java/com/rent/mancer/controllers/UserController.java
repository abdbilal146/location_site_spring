package com.rent.mancer.controllers;


import com.rent.mancer.models.User;
import com.rent.mancer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<User> addUser(
            @RequestBody User user,
            @AuthenticationPrincipal Jwt jwt
    ) {
        UUID userUid = UUID.fromString(jwt.getSubject());
        String email = jwt.getClaim("email");

        return ResponseEntity.ok(userService.addUser(user, userUid, email));
    }


    @PostMapping("/role")
    public ResponseEntity<String> getRole(
            @AuthenticationPrincipal Jwt jwt
    ){

        String sub = jwt.getSubject();
        return ResponseEntity.ok(userService.getRole(sub));
    }
}
