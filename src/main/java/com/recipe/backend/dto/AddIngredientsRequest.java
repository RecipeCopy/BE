package com.recipe.backend.dto;

import java.util.List;

public class AddIngredientsRequest {

    private Long userId; // 사용자 ID
    private List<String> ingredients; // 선택한 재료 목록

    // Getter와 Setter
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
