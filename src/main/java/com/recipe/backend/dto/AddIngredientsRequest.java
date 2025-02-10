package com.recipe.backend.dto;

import java.util.List;

public class AddIngredientsRequest {

    private List<String> ingredients; // 선택한 재료 목록



    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
