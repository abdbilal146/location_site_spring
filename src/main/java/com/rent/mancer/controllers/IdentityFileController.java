package com.rent.mancer.controllers;


import com.rent.mancer.models.IdentityFile;
import com.rent.mancer.services.IdentityFileService;
import com.rent.mancer.services.gcp.GcsSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/identity")
public class IdentityFileController {


    @Autowired
    private IdentityFileService identityFileService;



    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addNewIdentityFile(
            @RequestBody IdentityFile identityFile,
            @AuthenticationPrincipal Jwt jwt
    ) {
        // 1. Récupération de l'identité
        String sub = jwt.getSubject();
        UUID userUuid = UUID.fromString(sub);

        return ResponseEntity.ok(identityFileService.addNewIdentityFile(identityFile, userUuid));
    }
}
