package com.diamante.recommendation.client;

import com.diamante.recommendation.dto.RestaurantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "restaurant-service", path = "/api/restaurants")
public interface RestaurantClient {

    @GetMapping
    List<RestaurantDTO> findRestaurants(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minRating
    );
}
