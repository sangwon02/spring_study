package umc.study.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponseDTO {

    @Builder @Getter @NoArgsConstructor @AllArgsConstructor
    public static class AddReviewResultDTO {
        private Long reviewId;
        private Long storeId;
        private Long userId;
        private LocalDateTime createdAt;
    }

    @Builder @Getter @NoArgsConstructor @AllArgsConstructor
    public static class StoreReviewPreviewDTO {
        String ownerNickname;
        Float score;
        String body;
        LocalDate createdAt;
    }

    @Builder @Getter @NoArgsConstructor @AllArgsConstructor
    public static class StoreReviewPreviewListDTO {
        List<StoreReviewPreviewDTO> reviewList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder @Getter @NoArgsConstructor @AllArgsConstructor
    public static class UserReviewPreviewDTO {
        String storeName;
        Float score;
        String body;
        LocalDate createdAt;
    }

    @Builder @Getter @NoArgsConstructor @AllArgsConstructor
    public static class UserReviewPreviewListDTO {
        List<UserReviewPreviewDTO> reviewList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }
}