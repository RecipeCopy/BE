package com.recipe.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "recipe_scraps")
public class RecipeScrapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recipeName;

    @Column(nullable = false, length = 500)
    private String ingredients;

    @ElementCollection
    @CollectionTable(name = "recipe_scrap_steps", joinColumns = @JoinColumn(name = "recipe_scrap_id"))
    @Column(name = "step")
    private List<String> steps;

    @Column(nullable = false)
    private String userId; // 사용자 ID

    public RecipeScrapEntity(String userId, String recipeName, String ingredients, List<String> steps) {
        this.userId = userId;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.steps = steps;
    }
}
