package com.diamante.recommendation.dto;

import java.util.List;

public record UserPreferencesDTO(
        Long id,
        Double maxPrice,
        String preferredLocation,
        List<String> foodCategories
) {}
