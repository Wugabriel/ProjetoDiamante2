package com.diamante.recommendation.client;

import com.diamante.recommendation.dto.UserPreferencesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/api/users")
public interface UserClient {

    @GetMapping("/{id}/preferences")
    UserPreferencesDTO getPreferences(@PathVariable Long id);
}
