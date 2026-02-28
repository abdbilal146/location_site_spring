package com.rent.mancer.controllers;


import com.rent.mancer.models.Client;
import com.rent.mancer.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/client")
public class ClientController {


    @Autowired
    private ClientService clientService;


    @GetMapping
    public ResponseEntity<List<Client>> getAllClients(){
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @PostMapping("/add")
    public ResponseEntity<Client> addNewClient(
            @RequestBody Client client,
            @AuthenticationPrincipal Jwt jwt
            ){

        String sub = jwt.getSubject();
        UUID uid = UUID.fromString(sub);

        return ResponseEntity.ok(clientService.addNewClient(client,uid));
    }


    @DeleteMapping("/{id}")
    public void deleteClient(
            @PathVariable Long id
    ){
       clientService.deleteClient(id);
    }
}
