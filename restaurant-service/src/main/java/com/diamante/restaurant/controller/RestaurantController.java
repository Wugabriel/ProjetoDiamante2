package com.diamante.restaurant.controller;

import com.diamante.restaurant.entity.Restaurant;
import com.diamante.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping
    public List<Restaurant> list(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minRating) {
        return service.findAll(category, location, minRating);
    }

    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(restaurant));
    }

    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @Valid @RequestBody Restaurant restaurant) {
        restaurant.setId(id);
        return service.save(restaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
