//package com.recipe.backend.service;
//
//import com.recipe.backend.dto.RecipeDto;
//import com.recipe.backend.dto.RecipeScrapDto;
//import com.recipe.backend.entity.RecipeScrapEntity;
//import com.recipe.backend.repository.RecipeScrapRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class RecipeScrapService {
//
//    private final RecipeScrapRepository recipeScrapRepository;
//    private final RecipeService recipeService;
//
//    public RecipeScrapService(RecipeScrapRepository recipeScrapRepository, RecipeService recipeService) {
//        this.recipeScrapRepository = recipeScrapRepository;
//        this.recipeService = recipeService;
//    }
//
//    // ✅ 레시피 스크랩 추가 (이름만 받아와서 자동 저장)
//    public void scrapRecipe(String recipeName) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId = authentication.getName(); // 현재 로그인한 사용자 ID
//
//        // 이미 스크랩한 레시피인지 확인
//        if (recipeScrapRepository.existsByUserIdAndRecipeName(userId, recipeName)) {
//            throw new IllegalStateException("이미 스크랩한 레시피입니다.");
//        }
//
//        // 레시피 데이터 조회
//        Optional<RecipeDto> recipeData = recipeService.fetchRecipes()
//                .stream()
//                .filter(recipe -> recipe.getRecipeName().equalsIgnoreCase(recipeName))
//                .findFirst();
//
//        // 레시피가 존재하면 저장
//        if (recipeData.isPresent()) {
//            RecipeDto recipe = recipeData.get();
//            RecipeScrapEntity scrap = new RecipeScrapEntity(userId, recipe.getRecipeName(), recipe.getIngredients(), recipe.getSteps());
//            recipeScrapRepository.save(scrap);
//        } else {
//            throw new IllegalArgumentException("해당 레시피를 찾을 수 없습니다.");
//        }
//    }
//
//    // ✅ 레시피 스크랩 삭제
//    @Transactional
//    public void unScrapRecipe(String recipeName) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId = authentication.getName();
//
//        recipeScrapRepository.deleteByUserIdAndRecipeName(userId, recipeName);
//    }
//
//    // ✅ 사용자의 스크랩한 레시피 목록 조회
//    public List<RecipeScrapDto> getScrappedRecipes() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId = authentication.getName();
//
//        return recipeScrapRepository.findByUserId(userId)
//                .stream()
//                .map(scrap -> new RecipeScrapDto(scrap.getId(), scrap.getRecipeName(), scrap.getIngredients(), scrap.getSteps()))
//                .collect(Collectors.toList());
//    }
//}

package com.recipe.backend.service;

import com.recipe.backend.dto.RecipeDto;
import com.recipe.backend.dto.RecipeScrapDto;
import com.recipe.backend.entity.RecipeScrapEntity;
import com.recipe.backend.repository.RecipeScrapRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeScrapService {

    private final RecipeScrapRepository recipeScrapRepository;
    private final RecipeService recipeService;

    public RecipeScrapService(RecipeScrapRepository recipeScrapRepository, RecipeService recipeService) {
        this.recipeScrapRepository = recipeScrapRepository;
        this.recipeService = recipeService;
    }

    // ✅ 레시피 스크랩 추가 (사용자 ID를 헤더에서 받음)
    public void scrapRecipe(String kakaoId, String recipeName) {
        // 이미 스크랩한 레시피인지 확인
        if (recipeScrapRepository.existsByUserIdAndRecipeName(kakaoId, recipeName)) {
            throw new IllegalStateException("이미 스크랩한 레시피입니다.");
        }

        // 레시피 데이터 조회
        Optional<RecipeDto> recipeData = recipeService.fetchRecipes()
                .stream()
                .filter(recipe -> recipe.getRecipeName().equalsIgnoreCase(recipeName))
                .findFirst();

        // 레시피가 존재하면 저장
        if (recipeData.isPresent()) {
            RecipeDto recipe = recipeData.get();
            RecipeScrapEntity scrap = new RecipeScrapEntity(kakaoId, recipe.getRecipeName(), recipe.getIngredients(), recipe.getSteps());
            recipeScrapRepository.save(scrap);
        } else {
            throw new IllegalArgumentException("해당 레시피를 찾을 수 없습니다.");
        }
    }

    // ✅ 레시피 스크랩 삭제 (사용자 ID 헤더에서 받음)
    @Transactional
    public void unScrapRecipe(String kakaoId, String recipeName) {
        recipeScrapRepository.deleteByUserIdAndRecipeName(kakaoId, recipeName);
    }

    // ✅ 사용자의 스크랩한 레시피 목록 조회
    public List<RecipeScrapDto> getScrappedRecipes(String kakaoId) {
        return recipeScrapRepository.findByUserId(kakaoId)
                .stream()
                .map(scrap -> new RecipeScrapDto(scrap.getId(), scrap.getRecipeName(), scrap.getIngredients(), scrap.getSteps()))
                .collect(Collectors.toList());
    }
}
