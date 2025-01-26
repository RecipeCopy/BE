package com.recipe.backend.controller;

import com.recipe.backend.entity.*;
import com.recipe.backend.service.*;
import com.recipe.backend.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fridge")
@Tag(name = "User Fridge API", description = "사용자의 냉장고 관리 API")
public class UserFridgeController {

    private final UserFridgeService userFridgeService;

    public UserFridgeController(UserFridgeService userFridgeService) {
        this.userFridgeService = userFridgeService;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "사용자의 냉장고 조회", description = "특정 사용자의 냉장고에 저장된 재료 목록을 반환합니다.")
    public List<UserFridge> getUserFridge(@PathVariable Long userId) {
        return userFridgeService.getUserFridge(userId);
    }

    @PostMapping
    @Operation(summary = "냉장고에 재료 추가(아마 카메라로 인식하고 추가할때 사용할 예정..)", description = "특정 사용자의 냉장고에 하나의 재료를 추가합니다.")
    public UserFridge addIngredientToFridge(@RequestParam String ingredientName, @RequestParam Long userId) {
        return userFridgeService.addIngredientToFridge(ingredientName, userId);
    }

    @PostMapping("/add")
    @Operation(summary = "냉장고에 여러 재료 추가", description = "특정 사용자의 냉장고에 여러 재료를 한 번에 추가합니다.")
    public ResponseEntity<?> addIngredientsToFridge(@RequestBody AddIngredientsRequest request) {
        List<UserFridge> savedIngredients = userFridgeService.addIngredientsToFridge(request.getUserId(), request.getIngredients());
        return ResponseEntity.ok(new AddIngredientsResponse("Ingredients added to the fridge", savedIngredients));
    }
}
