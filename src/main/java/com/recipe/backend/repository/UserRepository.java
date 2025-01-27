package com.recipe.backend.repository;

import com.recipe.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByKakaoId(String kakaoId);
}
