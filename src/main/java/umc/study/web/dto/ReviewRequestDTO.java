package umc.study.web.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ReviewRequestDTO {

    @Getter
    public static class AddReviewDTO {
        @NotNull
        private Long userId;

        @NotBlank(message = "리뷰 내용은 비워둘 수 없습니다.")
        private String body;

        @NotNull(message = "평점은 필수입니다.")
        @DecimalMin(value = "0.0", message = "평점은 0.0 이상이어야 합니다.")
        @DecimalMax(value = "5.0", message = "평점은 5.0 이하이어야 합니다.")
        private Float score;
    }
}