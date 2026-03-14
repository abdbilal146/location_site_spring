package com.rent.mancer.services;


import com.rent.mancer.models.IdentityFile;
import com.rent.mancer.models.enums.FileUploadStatus;
import com.rent.mancer.repository.IdentityFileRepository;
import com.rent.mancer.services.gcp.GcsSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class IdentityFileService {


    @Autowired
    private IdentityFileRepository identityFileRepository;

    @Autowired
    private GcsSecurityService gcsSecurityService;

    public Map<String, String> addNewIdentityFile(
            IdentityFile identityFile,
            UUID uuid
    ){

        List<String> allowedTypes = Arrays.asList("image/jpeg", "image/png", "application/pdf");

        if (identityFile.getContentType() == null || !allowedTypes.contains(identityFile.getContentType())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Format de fichier non autorisé. Seuls JPG, PNG et PDF sont acceptés");
        }

        String fileName = "ids/" + identityFile.getClientId() + "/" + System.currentTimeMillis() + "_" + identityFile.getOriginalName();

        IdentityFile file = new IdentityFile();
        file.setUploadAt(LocalDateTime.now());
        file.setClientId(identityFile.getClientId());
        file.setUploadBy(uuid);
        file.setGcsPath(fileName);
        file.setFileUploadStatus(FileUploadStatus.PENDING);

        identityFileRepository.save(file);



        String signedUrl = gcsSecurityService.generateUploadUrl(fileName, identityFile.getContentType());



        Map<String, String> response = new HashMap<>();
        response.put("uploadUrl", signedUrl);
        response.put("fileName", fileName);

        return response;
    }



}
