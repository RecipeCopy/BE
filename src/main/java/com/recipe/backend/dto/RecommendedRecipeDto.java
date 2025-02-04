package com.recipe.backend.dto;

import java.util.List;

public class RecommendedRecipeDto extends RecipeDto { // 기존 RecipeDto 확장
    private List<String> matchedIngredients; // 보유한 재료 목록
    private int matchedCount;

    public RecommendedRecipeDto(String recipeName, String ingredients, List<String> steps, List<String> matchedIngredients) {
        super(recipeName, ingredients, steps);
        this.matchedIngredients = matchedIngredients;   // 재료 저장
        this.matchedCount = matchedIngredients.size();  // 개수 저장
    }

    public List<String> getMatchedIngredients() {
        return matchedIngredients;
    }

    public void setMatchedIngredients(List<String> matchedIngredients) {
        this.matchedIngredients = matchedIngredients;
        this.matchedCount = matchedIngredients.size();
    }

    public int getMatchedCount() {
        return matchedCount;
    }
}
