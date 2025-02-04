package com.recipe.backend.repository.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenUserRepository extends JpaRepository<com.recipe.backend.entity.token.TokenUserEntity, Long> {
    Optional<com.recipe.backend.entity.token.TokenUserEntity> findByUserId(String userId); // user_id로 검색

    boolean existsByUserId(String userId);
}
