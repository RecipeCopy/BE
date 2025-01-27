package com.recipe.backend.controller;

import com.recipe.backend.dto.KakaoUserInfoResponseDto;
import com.recipe.backend.dto.KakaoUserResponseDTO;
import com.recipe.backend.dto.ResponseDTO;
import com.recipe.backend.service.KakaoService;
import com.recipe.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final UserService userService; // UserService 추가

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        try {
            // Access Token 발급
            String accessToken = kakaoService.getAccessTokenFromKakao(code);
            log.info("Access Token: {}", accessToken);

            // 사용자 정보 가져오기
            KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
            log.info("Kakao User Info: {}", userInfo);

            // 사용자 정보 저장 또는 업데이트
            KakaoUserResponseDTO userResponse = userService.saveOrUpdateUser(userInfo);
            log.info("Saved User Info: {}", userResponse);

            return ResponseEntity.ok(userResponse); // 저장된 사용자 정보를 응답으로 반환

        } catch (Exception e) {
            log.error("Error during Kakao login: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kakao login failed");
        }
    }
}
