package com.diamante.recommendation.controller;

import com.diamante.recommendation.dto.RecommendationRequest;
import com.diamante.recommendation.dto.RecommendationResponse;
import com.diamante.recommendation.service.RecommendationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @PostMapping
    public RecommendationResponse recommend(@Valid @RequestBody RecommendationRequest request) {
        return service.recommend(request.userId());
    }

    @GetMapping("/{userId}")
    public RecommendationResponse recommendByPath(@PathVariable Long userId) {
        return service.recommend(userId);
    }
}
