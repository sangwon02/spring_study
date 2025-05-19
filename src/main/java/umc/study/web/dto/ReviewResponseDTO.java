package umc.study.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class ReviewResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddReviewResultDTO {
        private Long reviewId; // <-- 큰따옴표 제거
        private Long storeId;
        private Long userId;
        private LocalDateTime createdAt;
    }
}