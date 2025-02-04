package com.recipe.backend.repository;

import com.recipe.backend.entity.RecipeScrapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeScrapRepository extends JpaRepository<RecipeScrapEntity, Long> {
    List<RecipeScrapEntity> findByUserId(String userId);
    boolean existsByUserIdAndRecipeName(String userId, String recipeName);
    void deleteByUserIdAndRecipeName(String userId, String recipeName);
}
