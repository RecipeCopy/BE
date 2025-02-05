package com.recipe.backend.controller;


import com.recipe.backend.dto.AddIngredientsRequest;
import com.recipe.backend.dto.AddIngredientsResponse;
import com.recipe.backend.entity.UserFridge;
import com.recipe.backend.service.UserFridgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;


@RestController
@RequestMapping("/api/fridge")
@Tag(name = "User Fridge API", description = "사용자의 냉장고 관리 API")
@SecurityRequirement(name = "JWT TOKEN")
public class UserFridgeController {

    private final UserFridgeService userFridgeService;

    public UserFridgeController(UserFridgeService userFridgeService) {
        this.userFridgeService = userFridgeService;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "사용자의 냉장고 조회", description = "특정 사용자의 냉장고에 저장된 재료 목록을 반환합니다.",security = @SecurityRequirement(name = "JWT TOKEN"))
    public ResponseEntity<List<UserFridge>> getUserFridge(@PathVariable Long userId) {
        List<UserFridge> fridgeContents = userFridgeService.getUserFridge(userId);
        return ok(fridgeContents);
    }

    @PostMapping
    @Operation(summary = "냉장고에 재료 추가", description = "고정 재료에 없는 새로운 재료를 추가합니다. 이미 존재하는 재료는 추가되지 않습니다.",security = @SecurityRequirement(name = "JWT TOKEN"))
    public ResponseEntity<?> addIngredientToFridge(@RequestParam String ingredientName, @RequestParam Long userId) {
        try {
            if (userFridgeService.isIngredientAlreadyInFridge(ingredientName, userId)) {
                return status(HttpStatus.BAD_REQUEST).body("Ingredient already exists in the user's fridge");
            }
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
    @Operation(summary = "냉장고에 여러 재료 추가", description = "고정 재료 목록에서만 선택하여 여러 재료를 추가합니다. 이미 존재하는 재료는 추가되지 않습니다.",security = @SecurityRequirement(name = "JWT TOKEN"))
    public ResponseEntity<AddIngredientsResponse> addIngredientsToFridge(@RequestBody AddIngredientsRequest request) {
        try {
            List<String> fixedIngredients = userFridgeService.getFixedIngredients();

            // 유효한 재료만 필터링
            List<String> validIngredients = request.getIngredients().stream()
                    .filter(fixedIngredients::contains)
                    .toList();

            if (validIngredients.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AddIngredientsResponse("재료 목록에 없습니다. 카메라로 인식 후 추가하세요.", null));
            }

            // 필터링된 재료만 추가
            List<UserFridge> savedIngredients = userFridgeService.addIngredientsToFridge(request.getUserId(), validIngredients);
            AddIngredientsResponse response = new AddIngredientsResponse("재료가 추가되었습니다.", savedIngredients);
            return ResponseEntity.ok().body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AddIngredientsResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AddIngredientsResponse("An unexpected error occurred", null));
        }
    }

}
