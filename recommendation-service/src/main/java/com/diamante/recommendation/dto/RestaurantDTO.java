package com.diamante.recommendation.dto;

public record RestaurantDTO(
        Long id,
        String name,
        String category,
        String location,
        Double rating,
        String priceRange
) {}
