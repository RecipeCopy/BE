package com.recipe.backend.service.token;

import com.recipe.backend.dto.RecipeDto;
import com.recipe.backend.dto.token.ScrapRecipeDto;
import com.recipe.backend.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScrapService {
    private final Map<String, List<ScrapRecipeDto>> userScrapRecipes = new HashMap<>();
    private final RecipeService recipeService; // 기존 레시피 API 호출을 위한 의존성 추가

    public ScrapService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // 사용자별 스크랩 추가
    public boolean addScrapRecipe(String userId, String recipeName) {
        // 기존 API에서 레시피 정보를 검색
        Optional<RecipeDto> recipeOpt = recipeService.fetchRecipes()
                .stream()
                .filter(r -> r.getRecipeName().equals(recipeName))
                .findFirst();

        if (recipeOpt.isPresent()) {
            RecipeDto recipe = recipeOpt.get();
            ScrapRecipeDto scrapRecipe = new ScrapRecipeDto(recipe.getRecipeName(), recipe.getIngredients(), recipe.getSteps());

            // 사용자의 스크랩 목록 가져오기
            userScrapRecipes.putIfAbsent(userId, new ArrayList<>());
            List<ScrapRecipeDto> userScraps = userScrapRecipes.get(userId);

            // 중복 방지 후 추가
            if (userScraps.stream().noneMatch(r -> r.getRecipeName().equals(recipeName))) {
                userScraps.add(scrapRecipe);
                return true;
            }
        }
        return false;
    }

    // 사용자별 스크랩 목록 가져오기
    public List<ScrapRecipeDto> getScrapRecipes(String userId) {
        return userScrapRecipes.getOrDefault(userId, new ArrayList<>());
    }

    // 사용자별 특정 레시피 삭제
    public void removeScrapRecipe(String userId, String recipeName) {
        userScrapRecipes.getOrDefault(userId, new ArrayList<>())
                .removeIf(recipe -> recipe.getRecipeName().equals(recipeName));
    }
}