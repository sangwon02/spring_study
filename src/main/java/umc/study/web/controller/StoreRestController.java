package umc.study.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.study.apiPayload.ApiResponse;
import umc.study.converter.MissionConverter; // MissionConverter 임포트
import umc.study.converter.ReviewConverter;
import umc.study.domain.Mission;             // Mission 엔티티 임포트
import umc.study.domain.mapping.Review;
import umc.study.service.StoreService.StoreCommandService;
import umc.study.web.dto.MissionRequestDTO;  // MissionRequestDTO 임포트
import umc.study.web.dto.MissionResponseDTO; // MissionResponseDTO 임포트
import umc.study.web.dto.ReviewRequestDTO;
import umc.study.web.dto.ReviewResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreRestController {

    private final StoreCommandService storeCommandService;

    @PostMapping("/{storeId}/reviews")
    @Operation(summary = "가게에 리뷰 추가 API", description = "특정 가게에 사용자가 리뷰를 추가합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE4001", description = "가게를 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "사용자를 찾을 수 없습니다.")
    })
    @Parameters({
            @Parameter(name = "storeId", description = "리뷰를 추가할 가게의 아이디", required = true)
    })
    public ApiResponse<ReviewResponseDTO.AddReviewResultDTO> addReview(
            @PathVariable Long storeId,
            @RequestBody @Valid ReviewRequestDTO.AddReviewDTO request) {
        Review newReview = storeCommandService.addReviewToStore(storeId, request);
        return ApiResponse.onSuccess(ReviewConverter.toAddReviewResultDTO(newReview));
    }

    @PostMapping("/{storeId}/missions")
    @Operation(summary = "가게에 미션 추가 API", description = "특정 가게에 새로운 미션을 등록합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청 (입력값 검증 실패 등)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE4001", description = "가게를 찾을 수 없습니다.")
    })
    @Parameters({
            @Parameter(name = "storeId", description = "미션을 추가할 가게의 아이디", required = true)
    })
    public ApiResponse<MissionResponseDTO.AddMissionResultDTO> addMission(
            @PathVariable Long storeId,
            @RequestBody @Valid MissionRequestDTO.AddMissionDTO request) {
        Mission newMission = storeCommandService.addMissionToStore(storeId, request);
        return ApiResponse.onSuccess(MissionConverter.toAddMissionResultDTO(newMission));
    }
}