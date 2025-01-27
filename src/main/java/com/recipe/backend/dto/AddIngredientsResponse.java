package com.recipe.backend.dto;

import com.recipe.backend.entity.*;
import java.util.List;

public class AddIngredientsResponse {

    private String message; // 응답 메시지
    private List<UserFridge> savedIngredients; // 저장된 재료 목록

    // 기본 생성자 (필수)
    public AddIngredientsResponse() {}

    // 모든 필드를 초기화하는 생성자
    public AddIngredientsResponse(String message, List<UserFridge> savedIngredients) {
        this.message = message;
        this.savedIngredients = savedIngredients;
    }

    // Getter와 Setter (필수)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserFridge> getSavedIngredients() {
        return savedIngredients;
    }

    public void setSavedIngredients(List<UserFridge> savedIngredients) {
        this.savedIngredients = savedIngredients;
    }
}
