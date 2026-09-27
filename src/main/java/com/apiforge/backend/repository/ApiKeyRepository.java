package com.apiforge.backend.repository;

import com.apiforge.backend.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    // Look up an API key by its unique string value for request validation
    Optional<ApiKey> findByKeyValue(String keyValue);

}