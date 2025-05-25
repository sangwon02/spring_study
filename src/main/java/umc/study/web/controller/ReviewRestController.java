package umc.study.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.study.apiPayload.ApiResponse;
import umc.study.domain.mapping.Review;
import umc.study.service.ReviewService.ReviewCommandService;
import umc.study.service.ReviewService.ReviewQueryService;
import umc.study.validation.annotation.CheckPage;
import umc.study.validation.annotation.ExistStore;
import umc.study.validation.annotation.ExistUser;
import umc.study.converter.ReviewConverter;
import umc.study.web.dto.ReviewRequestDTO;
import umc.study.web.dto.ReviewResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/")
public class ReviewRestController {

    private final ReviewQueryService reviewQueryService;
    private final ReviewCommandService reviewCommandService;

    // --- 리뷰 생성 API ---
    @PostMapping("/stores/{storeId}/reviews")
    @Operation(summary = "가게에 리뷰 추가 API", description = "특정 가게에 사용자가 리뷰를 추가합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request (입력값 오류, 존재하지 않는 사용자 등)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Not Found (가게 없음)")
    })
    @Parameters({
            @Parameter(name = "storeId", description = "리뷰를 추가할 가게의 아이디", required = true)
    })
    public ApiResponse<ReviewResponseDTO.AddReviewResultDTO> addReview(
            @ExistStore @PathVariable Long storeId,
            @RequestBody @Valid ReviewRequestDTO.AddReviewDTO request) {
        Review review = reviewCommandService.createReview(storeId, request);
        return ApiResponse.onSuccess(ReviewConverter.toAddReviewResultDTO(review));
    }

    // --- 가게별 리뷰 조회 API ---
    @GetMapping("/stores/{storeId}/reviews")
    @Operation(summary = "특정 가게의 리뷰 목록 조회 API", description = "특정 가게의 리뷰 목록을 페이징하여 조회합니다. Query String으로 'page' 번호를 주세요 (1부터 시작).")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request (페이지 번호 오류)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Not Found (가게 없음)")
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디", required = true),
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작)", required = true)
    })
    public ApiResponse<ReviewResponseDTO.StoreReviewPreviewListDTO> getStoreReviews(
            @ExistStore @PathVariable Long storeId,
            @CheckPage @RequestParam Integer page) {
        Page<Review> reviewPage = reviewQueryService.getReviewListByStore(storeId, page - 1);
        return ApiResponse.onSuccess(ReviewConverter.toStoreReviewPreviewListDTO(reviewPage));
    }

    // --- 사용자별 리뷰 조회 API (내가 작성한 리뷰 목록) ---
    @GetMapping("/users/{userId}/reviews")
    @Operation(summary = "내가 작성한 리뷰 목록 조회 API", description = "내가 작성한 리뷰 목록을 페이징하여 조회합니다. Query String으로 'page' 번호를 주세요 (1부터 시작).")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request (페이지 번호 오류)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Not Found (사용자 없음)")
    })
    @Parameters({
            @Parameter(name = "userId", description = "사용자의 아이디", required = true),
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작)", required = true)
    })
    public ApiResponse<ReviewResponseDTO.UserReviewPreviewListDTO> getUserReviews(
            @ExistUser @PathVariable Long userId,
            @CheckPage @RequestParam Integer page) {
        Page<Review> reviewPage = reviewQueryService.getReviewListByUser(userId, page - 1);
        return ApiResponse.onSuccess(ReviewConverter.toUserReviewPreviewListDTO(reviewPage));
    }
}