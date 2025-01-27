package com.recipe.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/camera")
@Tag(name = "camera API", description = "카메라 API")
public class CameraController {

    @GetMapping("/open")
    @Operation(summary = "카메라로 재료 인식")
    public String openCamera() {
        // 실제 카메라 열기 로직은 프론트엔드에서 처리
        return "Camera opened successfully!";
    }


}
