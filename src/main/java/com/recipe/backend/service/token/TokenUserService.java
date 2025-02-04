package com.recipe.backend.service.token;


import com.recipe.backend.dto.token.AddUserRequestDTO;
import com.recipe.backend.repository.token.TokenUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenUserService {

    private final TokenUserRepository tokenuserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequestDTO request) {
        return tokenuserRepository.save(
                com.recipe.backend.entity.token.TokenUserEntity.builder()
                        .username(request.getUsername())
                        .userId(request.getUserId())
                        .password(bCryptPasswordEncoder.encode(request.getPassword()))
                        .build()
        ).getId();
    }

    // 사용자 ID 중복 확인
    public boolean isUserIdDuplicated(String userId) {
        return tokenuserRepository.existsByUserId(userId);
    }
}
