package com.recipe.backend.dto;

import com.recipe.backend.entity.*;
import java.util.List;

public class AddIngredientsResponse {
    private String message;
    private List<UserFridge> savedIngredients;

    public AddIngredientsResponse(String message, List<UserFridge> savedIngredients) {
        this.message = message;
        this.savedIngredients = savedIngredients;
    }


}


