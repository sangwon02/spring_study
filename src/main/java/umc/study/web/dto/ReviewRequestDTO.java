package umc.study.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax; // 별점 검증용
import jakarta.validation.constraints.DecimalMin; // 별점 검증용
import lombok.Getter;

public class ReviewRequestDTO {

    @Getter
    public static class AddReviewDTO {
        @NotNull
        private Long userId; // 리뷰를 작성하는 사용자 ID (실제로는 인증된 사용자 정보 사용)

        @NotBlank
        private String body; // 리뷰 내용

        @NotNull
        @DecimalMin(value = "0.0", message = "평점은 0.0 이상이어야 합니다.")
        @DecimalMax(value = "5.0", message = "평점은 5.0 이하이어야 합니다.")
        private Float score; // 평점 (0.0 ~ 5.0)
    }
}