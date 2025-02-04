package com.recipe.backend.dto;

import java.util.List;

public class RecipeScrapDto {
    private Long id; // 스크랩 ID
    private String recipeName;
    private String ingredients;
    private List<String> steps;

    public RecipeScrapDto(Long id, String recipeName, String ingredients, List<String> steps) {
        this.id = id;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    // Getter
    public Long getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }
}
