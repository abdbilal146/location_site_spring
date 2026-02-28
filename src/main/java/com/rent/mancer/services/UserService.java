package com.rent.mancer.services;

import com.rent.mancer.models.User;
import com.rent.mancer.models.enums.Role;
import com.rent.mancer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.server.UID;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    public User addUser(User user, UUID uid, String email) {
        User userEntity = new User();


        userEntity.setUid(uid);
        userEntity.setName(user.getName());
        userEntity.setFamilyName(user.getFamilyName());
        userEntity.setEmail(email);
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setRole(Role.USER);

        return userRepository.save(userEntity);
    }


    public String getRole(String sub){
        String role = "";
        UUID uid = UUID.fromString(sub);
        Optional<User> user = userRepository.findByUid(uid);

        if(user.isPresent()){
            role = user.get().getRole().name();
        }

        return role;
    }


}
