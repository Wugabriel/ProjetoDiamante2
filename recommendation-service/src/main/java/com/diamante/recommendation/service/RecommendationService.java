package com.diamante.recommendation.service;

import com.diamante.recommendation.client.RestaurantClient;
import com.diamante.recommendation.client.UserClient;
import com.diamante.recommendation.dto.RecommendationResponse;
import com.diamante.recommendation.dto.RestaurantDTO;
import com.diamante.recommendation.dto.UserPreferencesDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    private final RestaurantClient restaurantClient;
    private final UserClient userClient;
    private final ChatClient chatClient;

    public RecommendationService(RestaurantClient restaurantClient,
                                  UserClient userClient,
                                  ChatClient chatClient) {
        this.restaurantClient = restaurantClient;
        this.userClient = userClient;
        this.chatClient = chatClient;
    }

    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 500, multiplier = 2.0, maxDelay = 5000)
    )
    public RecommendationResponse recommend(Long userId) {
        UserPreferencesDTO prefs = userClient.getPreferences(userId);
        List<RestaurantDTO> restaurants = fetchRestaurants(prefs);
        String aiSuggestion = generateAiSuggestion(prefs, restaurants);
        return new RecommendationResponse(userId, restaurants, aiSuggestion);
    }

    private List<RestaurantDTO> fetchRestaurants(UserPreferencesDTO prefs) {
        String category = prefs.foodCategories() != null && !prefs.foodCategories().isEmpty()
                ? prefs.foodCategories().get(0) : null;
        return restaurantClient.findRestaurants(category, prefs.preferredLocation(), null);
    }

    private String generateAiSuggestion(UserPreferencesDTO prefs, List<RestaurantDTO> restaurants) {
        if (restaurants.isEmpty()) {
            return "Nenhum restaurante encontrado para as preferências informadas.";
        }

        String prompt = buildPrompt(prefs, restaurants);
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    private String buildPrompt(UserPreferencesDTO prefs, List<RestaurantDTO> restaurants) {
        StringBuilder sb = new StringBuilder();
        sb.append("Você é um assistente de recomendação de restaurantes. ");
        sb.append("Com base nas preferências do usuário e nos restaurantes disponíveis, ");
        sb.append("sugira os melhores e explique brevemente por quê.\n\n");
        sb.append("Preferências do usuário:\n");
        sb.append("- Categorias: ").append(prefs.foodCategories()).append("\n");
        sb.append("- Preço máximo: R$ ").append(prefs.maxPrice()).append("\n");
        sb.append("- Localização preferida: ").append(prefs.preferredLocation()).append("\n\n");
        sb.append("Restaurantes disponíveis:\n");
        restaurants.forEach(r ->
                sb.append("- ").append(r.name())
                  .append(" | Categoria: ").append(r.category())
                  .append(" | Localização: ").append(r.location())
                  .append(" | Avaliação: ").append(r.rating())
                  .append(" | Faixa de preço: ").append(r.priceRange())
                  .append("\n")
        );
        sb.append("\nIndique os top 3 restaurantes mais adequados e justifique.");
        return sb.toString();
    }
}
