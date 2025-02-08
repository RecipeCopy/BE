package com.recipe.backend.service;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IngredientService {

    private static final List<String> FIXED_INGREDIENTS = List.of(
            "감자", "고추", "계란", "김치", "당근", "돼지고기",
            "두부", "마늘", "버섯", "빵", "새우", "생선", "애호박", "양배추",
            "양파", "오이", "파스타면", "햄"
    );

    // 고정된 재료 목록 반환
    public List<String> getFixedIngredients() {
        return FIXED_INGREDIENTS;
    }
}
