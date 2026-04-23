package com.diamante.recommendation.dto;

import jakarta.validation.constraints.NotNull;

public record RecommendationRequest(
        @NotNull Long userId
) {}
