package com.recipe.backend.repository;


import com.recipe.backend.entity.UserFridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFridgeRepository extends JpaRepository<UserFridge, Long> {
    List<UserFridge> findByUserId(Long userId);
    boolean existsByIngredientNameAndUserId(String ingredientName, Long userId);
    Optional<UserFridge> findByIngredientNameAndUserId(String ingredientName, Long userId);

}
