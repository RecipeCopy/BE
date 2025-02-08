package com.recipe.backend.service;


import com.recipe.backend.entity.UserFridge;
import com.recipe.backend.repository.UserFridgeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserFridgeService {

    private final UserFridgeRepository userFridgeRepository;

    public UserFridgeService(UserFridgeRepository userFridgeRepository) {
        this.userFridgeRepository = userFridgeRepository;
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
}



