package com.recipe.backend.controller;

import com.recipe.backend.dto.RecipeDto;
import com.recipe.backend.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@Tag(name = "Recipe API", description = "레시피 API")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @Operation(summary = "전체 레시피 조회", description = "전체 레시피를 반환하는 API 입니다.")
    public List<RecipeDto> getRecipes() {
        return recipeService.fetchRecipes();
    }
}
