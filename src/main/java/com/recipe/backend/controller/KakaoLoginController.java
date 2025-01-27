package com.recipe.backend.controller;

import com.recipe.backend.dto.KakaoUserInfoResponseDto;
import com.recipe.backend.dto.KakaoUserResponseDTO;
import com.recipe.backend.service.KakaoService;
import com.recipe.backend.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final UserService userService;

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code, HttpSession session) {
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

            // 세션에 사용자 정보 저장
            session.setAttribute("loginUser", userResponse);

            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            log.error("Error during Kakao login: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kakao login failed");
        }
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok("로그아웃되었습니다.");
    }
}
