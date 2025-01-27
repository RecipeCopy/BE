package com.recipe.backend.dto;

import java.util.List;

public class RecipeDto {
    private String recipeName;  // 레시피 이름
    private String ingredients; // 재료 정보
    private List<String> steps; // 조리 단계

    public RecipeDto(String recipeName, String ingredients, List<String> steps) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    // Getter & Setter
    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}
