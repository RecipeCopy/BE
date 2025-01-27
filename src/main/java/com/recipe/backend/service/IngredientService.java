package com.recipe.backend.service;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IngredientService {

    private static final List<String> FIXED_INGREDIENTS = List.of(
            "애호박", "당근", "오이", "돼지고기", "생선",
            "계란", "고추", "양배추", "양파", "마늘",
            "김치", "새우", "버섯", "햄", "두부",
            "빵", "스파게티면"
    );

    // 고정된 재료 목록 반환
    public List<String> getFixedIngredients() {
        return FIXED_INGREDIENTS;
    }
}
