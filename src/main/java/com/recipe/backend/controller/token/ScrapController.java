package com.recipe.backend.controller.token;

import com.recipe.backend.dto.token.ScrapRecipeDto;
import com.recipe.backend.service.token.ScrapService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scraps")
public class ScrapController {
    private final ScrapService scrapService;

    public ScrapController(ScrapService scrapService) {
        this.scrapService = scrapService;
    }

    // 사용자가 스크랩한 레시피 목록 반환
    @Operation(summary = "스크랩 조회", description = "로그인된 사용자의 스크랩 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ScrapRecipeDto>> getScrapRecipes(Authentication authentication) {
        String userId = authentication.getName(); // 현재 인증된 사용자의 ID 가져오기
        return ResponseEntity.ok(scrapService.getScrapRecipes(userId));
    }

    // 레시피 이름으로 스크랩 추가
    @Operation(summary = "스크랩 추가", description = "로그인된 사용자가 원하는 레시피를 스크랩합니다.")
    @PostMapping("/{recipeName}")
    public ResponseEntity<String> addScrapRecipe(
            Authentication authentication,
            @PathVariable String recipeName) {
        String userId = authentication.getName(); // 현재 인증된 사용자의 ID 가져오기
        boolean success = scrapService.addScrapRecipe(userId, recipeName);
        if (success) {
            return ResponseEntity.ok("레시피 스크랩 성공: " + recipeName);
        }
        return ResponseEntity.badRequest().body("레시피를 찾을 수 없거나 이미 스크랩됨: " + recipeName);
    }

    // 특정 레시피 삭제
    @Operation(summary = "스크랩 삭제", description = "스크랩했던 레시피를 삭제합니다.")
    @DeleteMapping("/{recipeName}")
    public ResponseEntity<String> removeScrapRecipe(
            Authentication authentication,
            @PathVariable String recipeName) {
        String userId = authentication.getName(); // 현재 인증된 사용자의 ID 가져오기
        scrapService.removeScrapRecipe(userId, recipeName);
        return ResponseEntity.ok("레시피 삭제됨: " + recipeName);
    }
}
