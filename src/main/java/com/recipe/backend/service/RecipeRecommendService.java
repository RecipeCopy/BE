package com.recipe.backend.service;

import com.recipe.backend.dto.RecipeDto;
import com.recipe.backend.dto.RecommendedRecipeDto;
import com.recipe.backend.entity.UserFridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeRecommendService {
    private final UserFridgeService userFridgeService;
    private final RecipeService recipeService;

    @Autowired
    public RecipeRecommendService(UserFridgeService userFridgeService, RecipeService recipeService) {
        this.userFridgeService = userFridgeService;
        this.recipeService = recipeService;
    }

    public List<RecommendedRecipeDto> recommendRecipes(Long userId) {
        // 사용자의 냉장고에 있는 재료 가져오기
        List<UserFridge> userFridgeItems = userFridgeService.getUserFridge(userId);
        List<String> userIngredients = userFridgeItems.stream()
                .map(UserFridge::getIngredientName)
                .toList();

        // 전체 레시피 가져오기
        List<RecipeDto> allRecipes = recipeService.fetchRecipes();
        List<RecommendedRecipeDto> matchedRecipes = new ArrayList<>();

        for (RecipeDto recipe : allRecipes) {
            List<String> matchedIngredients = new ArrayList<>();

            // 재료 파싱 : 줄바꿈과 공백을 기준으로 분리
            String[] recipeIngredients = recipe.getIngredients().split("[\n,\\s]+");

            for (String ingredient : userIngredients) {
                for (String recipeIngredient : recipeIngredients) {
                    if (recipeIngredient.equalsIgnoreCase(ingredient)) { // 대소문자 구분 없이 비교
                        matchedIngredients.add(ingredient);
                    }
                }
            }

            if (!matchedIngredients.isEmpty()) {
                matchedRecipes.add(new RecommendedRecipeDto(
                        recipe.getRecipeName(),
                        recipe.getIngredients(),
                        recipe.getSteps(),
                        matchedIngredients
                ));
            }
        }

        return matchedRecipes;
    }
}
