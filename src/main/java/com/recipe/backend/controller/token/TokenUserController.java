package com.recipe.backend.controller.token;

import com.recipe.backend.entity.token.TokenUserEntity;
import com.recipe.backend.repository.token.TokenUserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class TokenUserController {

    private final TokenUserRepository userRepository;

    // 현재 인증된 사용자 정보 반환
    @Operation(summary = "로그인한 사용자 정보", description = "로그인한 사용자의 정보를 알려줍니다.")
    @GetMapping
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal TokenUserEntity user) {
        if (user == null) {
            return ResponseEntity.status(401).body("사용자가 인증되지 않았습니다.");
        }

        // 사용자 정보 반환
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "userId", user.getUserId(),
                "nickName", user.getUsername()
        ));
    }
}