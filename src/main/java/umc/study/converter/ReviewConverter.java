package umc.study.converter;

import umc.study.domain.Store;
import umc.study.domain.User;
import umc.study.domain.mapping.Review;
import umc.study.web.dto.ReviewRequestDTO;
import umc.study.web.dto.ReviewResponseDTO;

public class ReviewConverter {

    public static ReviewResponseDTO.AddReviewResultDTO toAddReviewResultDTO(Review review) {
        return ReviewResponseDTO.AddReviewResultDTO.builder()
                .reviewId(review.getReviewId())
                .storeId(review.getStore().getStoreId())
                .userId(review.getUser().getUserId())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static Review toReview(ReviewRequestDTO.AddReviewDTO request, User user, Store store) {
        return Review.builder()
                .body(request.getBody())
                .score(request.getScore())
                .user(user)
                .store(store)
                .build();
    }
}