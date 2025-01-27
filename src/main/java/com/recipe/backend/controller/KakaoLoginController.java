package com.recipe.backend.controller;

import com.recipe.backend.dto.KakaoUserInfoResponseDto;
import com.recipe.backend.dto.KakaoUserResponseDTO;
import com.recipe.backend.service.KakaoService;
import com.recipe.backend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final UserService userService;

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code, HttpServletResponse response, HttpSession session) {
        try {
            // 1. 카카오에서 Access Token 발급
            String accessToken = kakaoService.getAccessTokenFromKakao(code);
            log.info("Access Token: {}", accessToken);

            // 2. 카카오 사용자 정보 가져오기
            KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
            log.info("Kakao User Info: {}", userInfo);

            // 3. 사용자 정보 저장 또는 업데이트
            KakaoUserResponseDTO userResponse = userService.saveOrUpdateUser(userInfo);
            log.info("Saved User Info: {}", userResponse);

            // 세션에 사용자 정보 저장
            session.setAttribute("currentUser", userResponse);

            // 4. 프론트엔드로 리다이렉트하며 닉네임 전달
            String frontRedirectUri = "http://localhost:5173/main?nickName=" + URLEncoder.encode(userResponse.getNickName(), "UTF-8");
            response.sendRedirect(frontRedirectUri);
            return null; // 리다이렉트 이후 응답이 필요 없음
        } catch (Exception e) {
            log.error("Error during Kakao login: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok("로그아웃되었습니다.");
    }
}

