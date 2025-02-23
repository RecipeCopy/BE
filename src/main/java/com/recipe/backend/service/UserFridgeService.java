package com.recipe.backend.service;

import com.recipe.backend.entity.UserFridge;
import com.recipe.backend.repository.UserFridgeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserFridgeService {

    private final UserFridgeRepository userFridgeRepository;

    public UserFridgeService(UserFridgeRepository userFridgeRepository) {
        this.userFridgeRepository = userFridgeRepository;
    }

    public boolean deleteIngredientFromFridgeByToken(String ingredientName, String token) {
        Long userId = findUserIdByToken(token);
        // 해당 재료가 사용자의 냉장고에 존재하는지 확인합니다.
        Optional<UserFridge> ingredientOptional = userFridgeRepository.findByIngredientNameAndUserId(ingredientName, userId);
        if (ingredientOptional.isPresent()) {
            userFridgeRepository.delete(ingredientOptional.get());
            return true;
        }
        return false;
    }


    public List<UserFridge> getUserFridgeByToken(String token) {
        Long userId = findUserIdByToken(token);
        return getUserFridge(userId);
    }

    public boolean isIngredientAlreadyInFridgeByToken(String ingredientName, String token) {
        Long userId = findUserIdByToken(token);
        return isIngredientAlreadyInFridge(ingredientName, userId);
    }

    public UserFridge addIngredientToFridgeByToken(String ingredientName, String token) {
        Long userId = findUserIdByToken(token);
        return addIngredientToFridge(ingredientName, userId);
    }

    public List<UserFridge> addIngredientsToFridgeByToken(String token, List<String> ingredients) {
        Long userId = findUserIdByToken(token);
        return addIngredientsToFridge(userId, ingredients);
    }

    public List<UserFridge> getUserFridge(Long userId) {
        return userFridgeRepository.findByUserId(userId);
    }

    public boolean isIngredientAlreadyInFridge(String ingredientName, Long userId) {
        return userFridgeRepository.existsByIngredientNameAndUserId(ingredientName, userId);
    }

    public UserFridge addIngredientToFridge(String ingredientName, Long userId) {
        if (!userFridgeRepository.existsByIngredientNameAndUserId(ingredientName, userId)) {
            return userFridgeRepository.save(new UserFridge(ingredientName, userId));
        }
        throw new IllegalArgumentException("Ingredient already exists in the user's fridge");
    }

    public List<UserFridge> addIngredientsToFridge(Long userId, List<String> ingredients) {
        List<UserFridge> savedIngredients = new ArrayList<>();
        for (String ingredient : ingredients) {
            // 중복 체크
            if (!userFridgeRepository.existsByIngredientNameAndUserId(ingredient, userId)) {
                UserFridge userFridge = new UserFridge(ingredient, userId);
                savedIngredients.add(userFridgeRepository.save(userFridge));
            }
        }
        return savedIngredients;
    }

    public List<String> getFixedIngredients() {
        return List.of(
                "감자", "고추", "계란", "김치", "당근", "돼지고기",
                "두부", "마늘", "버섯", "빵", "새우", "생선", "애호박", "양배추",
                "양파", "오이", "파스타면", "햄"
        );
    }

    private Long findUserIdByToken(String token) {
        // 토큰으로 사용자 ID를 찾는 로직 (예: JWT에서 추출 또는 매핑 데이터 사용)
        // 예제: 간단히 토큰 해시값을 userId로 변환
        return token.hashCode() & 0xfffffffL;
    }
}
