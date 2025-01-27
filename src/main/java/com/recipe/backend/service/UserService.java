package com.recipe.backend.service;

import com.recipe.backend.dto.KakaoUserResponseDTO;
import com.recipe.backend.dto.KakaoUserInfoResponseDto;
import com.recipe.backend.entity.UserEntity;
import com.recipe.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public KakaoUserResponseDTO saveOrUpdateUser(KakaoUserInfoResponseDto userInfo) {
        try {
            String kakaoId = userInfo.getId().toString();
            log.info("Received Kakao User ID: {}", kakaoId);

            // 데이터베이스에서 사용자 검색
            UserEntity user = userRepository.findByKakaoId(kakaoId);

            if (user == null) {
                // 새 사용자 생성
                log.info("Creating new user for Kakao ID: {}", kakaoId);

                String email = userInfo.getKakaoAccount().getEmail();
                if (email == null) {
                    log.warn("Email is null for user: {}", kakaoId);
                    email = "unknown@domain.com"; // 기본값 설정
                }

                user = new UserEntity(
                        kakaoId,
                        userInfo.getKakaoAccount().getProfile().getNickName(),
                        email,
                        userInfo.getKakaoAccount().getProfile().getProfileImageUrl()
                );
            } else {
                // 기존 사용자 정보 업데이트 (Setter 사용하지 않음)
                log.info("Updating existing user for Kakao ID: {}", kakaoId);

                String email = userInfo.getKakaoAccount().getEmail();
                if (email == null) {
                    log.warn("Email is null for user: {}", kakaoId);
                    email = "unknown@domain.com";
                }

                user.updateNickName(userInfo.getKakaoAccount().getProfile().getNickName())
                        .updateEmail(email)
                        .updateProfileImageUrl(userInfo.getKakaoAccount().getProfile().getProfileImageUrl());
            }

            // 사용자 저장
            UserEntity savedUser = userRepository.save(user);
            log.info("Saved User: {}", savedUser);

            return KakaoUserResponseDTO.fromEntity(savedUser); // 저장된 사용자 정보를 반환
        } catch (Exception e) {
            log.error("Error saving or updating user: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save or update user", e);
        }
    }
}
