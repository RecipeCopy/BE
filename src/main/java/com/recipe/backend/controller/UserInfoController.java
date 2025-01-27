package com.recipe.backend.controller;


import com.recipe.backend.dto.KakaoUserResponseDTO;
import com.recipe.backend.dto.ResponseDTO;
import com.recipe.backend.entity.UserEntity;
import com.recipe.backend.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserInfoController {

//    @GetMapping("/user")
//    public ResponseEntity<?> getUserInfo(HttpSession session) {
//        KakaoUserResponseDTO loginUser = (KakaoUserResponseDTO) session.getAttribute("loginUser");
//
//        if (loginUser == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인된 사용자가 없습니다.");
//        }
//
//        return ResponseEntity.ok(loginUser);
//    }


    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        KakaoUserResponseDTO currentUser = (KakaoUserResponseDTO) session.getAttribute("currentUser");

        // 세션에 정보가 없을 경우 처리
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO<>(
                    HttpStatus.UNAUTHORIZED.value(),
                    "로그인 정보가 없습니다.",
                    null
            ));
        }

        // 현재 사용자 정보 반환
        return ResponseEntity.ok(new ResponseDTO<>(
                HttpStatus.OK.value(),
                "현재 로그인한 사용자 정보입니다.",
                currentUser
        ));
    }
}
