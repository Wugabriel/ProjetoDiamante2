package com.diamante.restaurant.service;

import com.diamante.restaurant.entity.Restaurant;
import com.diamante.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public List<Restaurant> findAll(String category, String location, Double minRating) {
        return repository.findWithFilters(category, location, minRating);
    }

    public Restaurant findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found: " + id));
    }

    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public void delete(Long id) {
        repository.findById(id).ifPresent(r -> {
            r.setActive(false);
            repository.save(r);
        });
    }
}
