package com.rent.mancer.services.gcp;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.HttpMethod;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class GcsSecurityService {

    private Storage storage;

    @Value("${gcp.bucket.name}")
    private String bucketName;

    // On utilise une propriété personnalisée pour le chemin du fichier
    @Value("${gcp.config.file}")
    private String gcpConfigFile;

    @PostConstruct
    public void init() throws IOException {
        InputStream is;

        if (gcpConfigFile.startsWith("/") || gcpConfigFile.contains(":")) {
            is = new FileInputStream(gcpConfigFile);
        } else {
            is = new ClassPathResource(gcpConfigFile).getInputStream();
        }

        this.storage = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(is))
                .build()
                .getService();
    }

    public String generateUploadUrl(String objectName, String contentType) {
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, objectName).build();

        // Correction : Google Cloud Storage attend souvent "Content-Type" (avec une majuscule au T)
        Map<String, String> extensionHeaders = new HashMap<>();
        extensionHeaders.put("Content-Type", contentType);

        URL signedUrl = storage.signUrl(
                blobInfo,
                10,
                TimeUnit.MINUTES,
                Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
                Storage.SignUrlOption.withExtHeaders(extensionHeaders),
                Storage.SignUrlOption.withV4Signature()
        );

        return signedUrl.toString();
    }
}