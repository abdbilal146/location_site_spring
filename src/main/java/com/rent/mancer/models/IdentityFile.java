package com.rent.mancer.models;


import com.rent.mancer.models.enums.FileUploadStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@Entity
@Table(name = "identity_file")
public class IdentityFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;

    private UUID uploadBy;

    private LocalDateTime uploadAt;

    private String originalName;

    private String contentType;

    private String gcsPath;

    @Enumerated(EnumType.STRING)
    private FileUploadStatus fileUploadStatus;


}
