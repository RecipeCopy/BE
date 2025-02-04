//package com.recipe.backend.controller;
//
//import com.recipe.backend.dto.RecipeScrapDto;
//import com.recipe.backend.service.RecipeScrapService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/recipes/scrap")
//public class RecipeScrapController {
//
//    private final RecipeScrapService recipeScrapService;
//
//    public RecipeScrapController(RecipeScrapService recipeScrapService) {
//        this.recipeScrapService = recipeScrapService;
//    }
//
//    // ✅ 레시피 스크랩 추가 (이름만 요청)
//    @PostMapping
//    public ResponseEntity<String> scrapRecipe(@RequestParam String recipeName) {
//        recipeScrapService.scrapRecipe(recipeName);
//        return ResponseEntity.ok("레시피 스크랩 성공");
//    }
//
//    // ✅ 레시피 스크랩 삭제
//    @DeleteMapping
//    public ResponseEntity<String> unScrapRecipe(@RequestParam String recipeName) {
//        recipeScrapService.unScrapRecipe(recipeName);
//        return ResponseEntity.ok("레시피 스크랩 취소 성공");
//    }
//
//    // ✅ 사용자의 스크랩한 레시피 목록 조회
//    @GetMapping
//    public ResponseEntity<List<RecipeScrapDto>> getScrappedRecipes() {
//        List<RecipeScrapDto> scrappedRecipes = recipeScrapService.getScrappedRecipes();
//        return ResponseEntity.ok(scrappedRecipes);
//    }
//}

package com.recipe.backend.controller;

import com.recipe.backend.dto.RecipeScrapDto;
import com.recipe.backend.service.RecipeScrapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes/scrap")
public class RecipeScrapController {

    private final RecipeScrapService recipeScrapService;

    public RecipeScrapController(RecipeScrapService recipeScrapService) {
        this.recipeScrapService = recipeScrapService;
    }

    // ✅ 레시피 스크랩 추가 (이름만 요청)
    @PostMapping
    public ResponseEntity<String> scrapRecipe(@RequestHeader("kakaoId") String kakaoId, @RequestParam String recipeName) {
        recipeScrapService.scrapRecipe(kakaoId, recipeName);
        return ResponseEntity.ok("레시피 스크랩 성공");
    }

    // ✅ 레시피 스크랩 삭제
    @DeleteMapping
    public ResponseEntity<String> unScrapRecipe(@RequestHeader("kakaoId") String kakaoId, @RequestParam String recipeName) {
        recipeScrapService.unScrapRecipe(kakaoId, recipeName);
        return ResponseEntity.ok("레시피 스크랩 취소 성공");
    }

    // ✅ 사용자의 스크랩한 레시피 목록 조회
    @GetMapping
    public ResponseEntity<List<RecipeScrapDto>> getScrappedRecipes(@RequestHeader("kakaoId") String kakaoId) {
        List<RecipeScrapDto> scrappedRecipes = recipeScrapService.getScrappedRecipes(kakaoId);
        return ResponseEntity.ok(scrappedRecipes);
    }
}
