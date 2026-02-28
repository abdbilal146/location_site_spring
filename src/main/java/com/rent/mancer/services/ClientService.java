package com.rent.mancer.services;


import com.rent.mancer.models.Client;
import com.rent.mancer.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    public Client addNewClient(
            Client client,
            UUID createdBy
    ){
        Client newClient = new Client();
        newClient.setFullName(client.getFullName());
        newClient.setEmail(client.getEmail());
        newClient.setPhoneNumber(client.getPhoneNumber());
        newClient.setAddress(client.getAddress());
        newClient.setAccountStatus(client.getAccountStatus());
        newClient.setCreatedAt(LocalDateTime.now());
        newClient.setCreatedBy(createdBy);

        return clientRepository.save(newClient);
    }


    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public void deleteClient(
            Long id
    ){
        clientRepository.deleteById(id);
    }
}
