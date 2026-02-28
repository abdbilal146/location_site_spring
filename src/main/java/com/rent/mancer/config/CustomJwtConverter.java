package com.rent.mancer.config;

import com.rent.mancer.models.User;
import com.rent.mancer.repository.UserRepository;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;


@Component
public class CustomJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserRepository userRepository;

    public CustomJwtConverter(UserRepository userRepository){
        this.userRepository = userRepository;
    }



    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String sub = jwt.getSubject();

        if (sub != null) {
            try {
                UUID uid = UUID.fromString(sub);
                // Utilise findByUid si l'ID n'est pas l'UUID Supabase
                Optional<User> userOpt = userRepository.findByUid(uid);

                if (userOpt.isPresent()) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + userOpt.get().getRole().name()));
                } else {
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }
            } catch (Exception e) {
                // Log l'erreur si besoin mais ne bloque pas la requête
            }
        }
        return new JwtAuthenticationToken(jwt, authorities);
    }
}
