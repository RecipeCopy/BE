package com.recipe.backend.controller;

import com.recipe.backend.entity.*;
import com.recipe.backend.service.*;
import com.recipe.backend.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/fridge")
@Tag(name = "User Fridge API", description = "사용자의 냉장고 관리 API")
public class UserFridgeController {

    private final UserFridgeService userFridgeService;

    public UserFridgeController(UserFridgeService userFridgeService) {
        this.userFridgeService = userFridgeService;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "사용자의 냉장고 조회", description = "특정 사용자의 냉장고에 저장된 재료 목록을 반환합니다.")
    public ResponseEntity<List<UserFridge>> getUserFridge(@PathVariable Long userId) {
        List<UserFridge> fridgeContents = userFridgeService.getUserFridge(userId);
        return ok(fridgeContents);
    }

    @PostMapping
    @Operation(summary = "냉장고에 재료 추가", description = "특정 사용자의 냉장고에 하나의 재료를 추가합니다.")
    public ResponseEntity<?> addIngredientToFridge(@RequestParam String ingredientName, @RequestParam Long userId) {
        try {
            UserFridge savedIngredient = userFridgeService.addIngredientToFridge(ingredientName, userId);
            return ok(savedIngredient);
        } catch (IllegalArgumentException e) {
            return status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping(
            value = "/add",
            consumes = "application/json",
            produces = "application/json"
    )
    @Operation(summary = "냉장고에 여러 재료 추가", description = "특정 사용자의 냉장고에 여러 재료를 한 번에 추가합니다.")
    public ResponseEntity<AddIngredientsResponse> addIngredientsToFridge(@RequestBody AddIngredientsRequest request) {
        try {
            List<UserFridge> savedIngredients = userFridgeService.addIngredientsToFridge(request.getUserId(), request.getIngredients());
            AddIngredientsResponse response = new AddIngredientsResponse("재료가 추가되었습니다.", savedIngredients);
            return ResponseEntity.ok().body(response); // JSON 응답 명시
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AddIngredientsResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AddIngredientsResponse("An unexpected error occurred", null));
        }
    }
}
