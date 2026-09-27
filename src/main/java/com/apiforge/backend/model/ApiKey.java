package com.apiforge.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "api_keys")
@Data
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String keyValue; // The actual generated API key string

    @Column(nullable = false)
    private String projectName; // e.g., "Internal Analytics Service"

    @Column(nullable = false)
    private String environment; // "DEV" or "PROD"

    private int quotaLimit = 1000; // Max requests allowed per period
    private int requestCount = 0;   // Tracked usage

    private boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();
}