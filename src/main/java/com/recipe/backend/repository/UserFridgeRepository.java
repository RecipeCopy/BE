package com.recipe.backend.repository;

import com.recipe.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFridgeRepository extends JpaRepository<UserFridge, Long> {
    List<UserFridge> findByUserId(Long userId);
    boolean existsByIngredientNameAndUserId(String ingredientName, Long userId);
}
