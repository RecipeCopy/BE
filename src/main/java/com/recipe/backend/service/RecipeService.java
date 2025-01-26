package com.recipe.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.backend.dto.RecipeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {
    private final RestTemplate restTemplate;
    private final String apiKey;

    public RecipeService(RestTemplate restTemplate, @Value("${api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public List<RecipeDto> fetchRecipes() {
        String apiUrl = String.format(
                "http://openapi.foodsafetykorea.go.kr/api/%s/COOKRCP01/json/1/300", // API URL
                apiKey
        );
        try {
            // API 호출
            String response = restTemplate.getForObject(apiUrl, String.class);

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            JsonNode rows = root.path("COOKRCP01").path("row");

            List<RecipeDto> recipes = new ArrayList<>();
            for (JsonNode row : rows) {
                // 필요한 데이터 추출
                String recipeName = row.path("RCP_NM").asText();
                String ingredients = row.path("RCP_PARTS_DTLS").asText();

                // 조리 단계 추출
                List<String> steps = new ArrayList<>();
                for (int i = 1; i <= 20; i++) {
                    String step = row.path("MANUAL" + String.format("%02d", i)).asText();
                    if (step != null && !step.isEmpty()) {
                        steps.add(step);
                    }
                }

                // DTO 생성
                recipes.add(new RecipeDto(recipeName, ingredients, steps));
            }

            return recipes;
        } catch (Exception e) {
            throw new RuntimeException("API 호출 또는 JSON 파싱 실패", e);
        }
    }
}
