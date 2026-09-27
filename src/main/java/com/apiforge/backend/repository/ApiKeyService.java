package com.apiforge.backend.service;

import com.apiforge.backend.model.ApiKey;
import com.apiforge.backend.repository.ApiKeyRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    // 1. Generate and issue a new API key for a project environment
    public ApiKey createApiKey(String projectName, String environment, int quotaLimit) {
        ApiKey apiKey = new ApiKey();
        // Generate a unique token string prefixed with 'af_'
        apiKey.setKeyValue("af_" + UUID.randomUUID().toString().replace("-", ""));
        apiKey.setProjectName(projectName);
        apiKey.setEnvironment(environment);
        apiKey.setQuotaLimit(quotaLimit);
        apiKey.setActive(true);

        return apiKeyRepository.save(apiKey);
    }

    // 2. Retrieve all keys for administrative monitoring
    public List<ApiKey> getAllApiKeys() {
        return apiKeyRepository.findAll();
    }

    // 3. Enforce quotas and track request execution
    public boolean validateAndConsumeQuota(String keyValue) {
        ApiKey apiKey = apiKeyRepository.findByKeyValue(keyValue).orElse(null);

        if (apiKey == null || !apiKey.isActive()) {
            return false; // Invalid or inactive key
        }

        // Check if quota limit has been reached
        if (apiKey.getRequestCount() >= apiKey.getQuotaLimit()) {
            return false; // Quota exceeded
        }

        // Increment usage count and persist tracking data
        apiKey.setRequestCount(apiKey.getRequestCount() + 1);
        apiKeyRepository.save(apiKey);

        return true;
    }
}