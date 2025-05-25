package umc.study.converter;

import org.springframework.data.domain.Page;
import umc.study.domain.Store;
import umc.study.domain.User;
import umc.study.domain.mapping.Review;
import umc.study.web.dto.ReviewRequestDTO;
import umc.study.web.dto.ReviewResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    // --- 리뷰 생성 로직 ---
    public static Review toReview(ReviewRequestDTO.AddReviewDTO request, User user, Store store){
        return Review.builder()
                .body(request.getBody())
                .score(request.getScore())
                .user(user)
                .store(store)
                .build();
    }

    public static ReviewResponseDTO.AddReviewResultDTO toAddReviewResultDTO(Review review){
        return ReviewResponseDTO.AddReviewResultDTO.builder()
                .reviewId(review.getReviewId())
                .storeId(review.getStore().getStoreId())
                .userId(review.getUser().getUserId())
                .createdAt(review.getCreatedAt())
                .build();
    }

    // --- 목록 조회 로직 ---
    public static ReviewResponseDTO.StoreReviewPreviewDTO toStoreReviewPreviewDTO(Review review){
        return ReviewResponseDTO.StoreReviewPreviewDTO.builder()
                .ownerNickname(review.getUser().getName())
                .score(review.getScore())
                .createdAt(review.getCreatedAt().toLocalDate())
                .body(review.getBody())
                .build();
    }

    public static ReviewResponseDTO.StoreReviewPreviewListDTO toStoreReviewPreviewListDTO(Page<Review> reviewList){
        List<ReviewResponseDTO.StoreReviewPreviewDTO> dtoList = reviewList.stream()
                .map(ReviewConverter::toStoreReviewPreviewDTO).collect(Collectors.toList());
        return ReviewResponseDTO.StoreReviewPreviewListDTO.builder()
                .reviewList(dtoList)
                .listSize(dtoList.size())
                .totalPage(reviewList.getTotalPages())
                .totalElements(reviewList.getTotalElements())
                .isFirst(reviewList.isFirst())
                .isLast(reviewList.isLast())
                .build();
    }

    public static ReviewResponseDTO.UserReviewPreviewDTO toUserReviewPreviewDTO(Review review){
        return ReviewResponseDTO.UserReviewPreviewDTO.builder()
                .storeName(review.getStore().getName())
                .score(review.getScore())
                .createdAt(review.getCreatedAt().toLocalDate())
                .body(review.getBody())
                .build();
    }

    public static ReviewResponseDTO.UserReviewPreviewListDTO toUserReviewPreviewListDTO(Page<Review> reviewList){
        List<ReviewResponseDTO.UserReviewPreviewDTO> dtoList = reviewList.stream()
                .map(ReviewConverter::toUserReviewPreviewDTO).collect(Collectors.toList());
        return ReviewResponseDTO.UserReviewPreviewListDTO.builder()
                .reviewList(dtoList)
                .listSize(dtoList.size())
                .totalPage(reviewList.getTotalPages())
                .totalElements(reviewList.getTotalElements())
                .isFirst(reviewList.isFirst())
                .isLast(reviewList.isLast())
                .build();
    }
}