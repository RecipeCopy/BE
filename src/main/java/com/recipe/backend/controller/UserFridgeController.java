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

    @GetMapping
    @Operation(summary = "사용자의 냉장고 조회", description = "JWT 토큰을 사용하여 사용자의 냉장고 데이터를 조회합니다.", security = @SecurityRequirement(name = "JWT TOKEN"))
    public ResponseEntity<List<UserFridge>> getUserFridge(@RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", ""); // Bearer 제거
        List<UserFridge> fridgeContents = userFridgeService.getUserFridgeByToken(jwtToken);
        return ResponseEntity.ok(fridgeContents);
    }

    @PostMapping
    @Operation(summary = "냉장고에 재료 추가", description = "JWT 토큰을 사용하여 냉장고에 재료를 추가합니다.", security = @SecurityRequirement(name = "JWT TOKEN"))
    public ResponseEntity<?> addIngredientToFridge(@RequestParam String ingredientName, @RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", ""); // Bearer 제거
        try {
            if (userFridgeService.isIngredientAlreadyInFridgeByToken(ingredientName, jwtToken)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 추가된 재료입니다.");
            }
            UserFridge savedIngredient = userFridgeService.addIngredientToFridgeByToken(ingredientName, jwtToken);
            return ResponseEntity.ok(savedIngredient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    @Operation(summary = "냉장고에 여러 재료 추가", description = "JWT 토큰을 사용하여 여러 재료를 추가합니다.", security = @SecurityRequirement(name = "JWT TOKEN"))
    public ResponseEntity<AddIngredientsResponse> addIngredientsToFridge(@RequestBody AddIngredientsRequest request, @RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", ""); // Bearer 제거
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
            List<UserFridge> savedIngredients = userFridgeService.addIngredientsToFridgeByToken(jwtToken, validIngredients);
            AddIngredientsResponse response = new AddIngredientsResponse("재료가 추가되었습니다.", savedIngredients);
            return ResponseEntity.ok().body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AddIngredientsResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AddIngredientsResponse("An unexpected error occurred", null));
        }
    }
}
