package com.apiforge.backend.controller;

import com.apiforge.backend.model.ApiKey;
import com.apiforge.backend.service.ApiKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keys")
@CrossOrigin(origins = "*") // Allows frontend integration
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    public ApiKeyController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    // 1. Issue a new project environment API key with specific quotas
    @PostMapping("/generate")
    public ResponseEntity<ApiKey> generateKey(
            @RequestParam String projectName,
            @RequestParam String environment,
            @RequestParam int quotaLimit) {
        ApiKey newKey = apiKeyService.createApiKey(projectName, environment, quotaLimit);
        return ResponseEntity.ok(newKey);
    }

    // 2. Monitor all API keys, usage counts, and metrics
    @GetMapping
    public ResponseEntity<List<ApiKey>> getAllKeys() {
        List<ApiKey> keys = apiKeyService.getAllApiKeys();
        return ResponseEntity.ok(keys);
    }

    // 3. Simulate an incoming client API request using an API key (enforces quota & tracks usage)
    @PostMapping("/invoke")
    public ResponseEntity<String> invokeApiRequest(@RequestHeader("X-API-KEY") String keyValue) {
        boolean allowed = apiKeyService.validateAndConsumeQuota(keyValue);

        if (!allowed) {
            return ResponseEntity.status(403).body("Access Denied: Invalid API key, inactive status, or quota limit exceeded.");
        }

        return ResponseEntity.ok("API Request Successful! Usage tracked and quota checked successfully.");
    }
}