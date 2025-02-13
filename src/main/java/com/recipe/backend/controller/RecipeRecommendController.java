package com.recipe.backend.controller;

import com.recipe.backend.dto.RecommendedRecipeDto;
import com.recipe.backend.service.RecipeRecommendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@Tag(name = "Recipe Recommend API", description = "레시피 추천 API")
@SecurityRequirement(name = "JWT TOKEN") // JWT 보안 적용
public class RecipeRecommendController {
    private final RecipeRecommendService recipeRecommendService;

    @Autowired
    public RecipeRecommendController(RecipeRecommendService recipeRecommendService) {
        this.recipeRecommendService = recipeRecommendService;
    }

    @GetMapping("/recommend")
    @Operation(summary = "사용자의 냉장고 기반 레시피 추천", description = "JWT 토큰을 사용하여 냉장고 재료를 기반으로 레시피를 추천합니다.", security = @SecurityRequirement(name = "JWT TOKEN"))
    public ResponseEntity<List<RecommendedRecipeDto>> recommendRecipes(@RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", ""); // Bearer 제거
        List<RecommendedRecipeDto> recommendedRecipes = recipeRecommendService.recommendRecipes(jwtToken);
        return ResponseEntity.ok(recommendedRecipes);
    }
}
