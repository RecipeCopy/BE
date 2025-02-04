package com.recipe.backend.controller;

import com.recipe.backend.dto.RecommendedRecipeDto;
import com.recipe.backend.service.RecipeRecommendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@Tag(name = "Recipe Recommend API", description = "레시피 추천 API")
public class RecipeRecommendController {
    private final RecipeRecommendService recipeRecommendService;

    @Autowired
    public RecipeRecommendController(RecipeRecommendService recipeRecommendService) {
        this.recipeRecommendService = recipeRecommendService;
    }

    @GetMapping("/recommend/{userId}")
    @Operation(summary = "사용자의 냉장고 기반 레시피 추천", description = "사용자의 냉장고 재료를 기반으로 레시피를 추천합니다.")
    public List<RecommendedRecipeDto> recommendRecipes(@PathVariable Long userId) {
        return recipeRecommendService.recommendRecipes(userId);
    }
}
