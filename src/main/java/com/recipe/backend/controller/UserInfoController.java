package com.recipe.backend.controller;


import com.recipe.backend.dto.KakaoUserResponseDTO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserInfoController {

    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(HttpSession session) {
        KakaoUserResponseDTO loginUser = (KakaoUserResponseDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인된 사용자가 없습니다.");
        }

        return ResponseEntity.ok(loginUser);
    }
}
