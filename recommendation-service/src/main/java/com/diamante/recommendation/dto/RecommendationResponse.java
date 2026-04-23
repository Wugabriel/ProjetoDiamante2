package com.diamante.recommendation.dto;

import java.util.List;

public record RecommendationResponse(
        Long userId,
        List<RestaurantDTO> restaurants,
        String aiSuggestion
) {}
