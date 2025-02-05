package com.recipe.backend.controller;


import com.recipe.backend.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@Tag(name = "Ingredient API", description = "재료 관련 API")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/list")
    @Operation(summary = "고정 재료 목록 조회", description = "미리 정의된 고정 재료 목록을 반환하는 API입니다.")
    public List<String> getFixedIngredients() {
        return ingredientService.getFixedIngredients();
    }
}
