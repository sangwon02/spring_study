package umc.study.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.apiPayload.code.status.ErrorStatus;
import umc.study.apiPayload.exception.handler.StoreHandler;
import umc.study.apiPayload.exception.handler.UserHandler;
import umc.study.converter.ReviewConverter;
import umc.study.domain.Store;
import umc.study.domain.User;
import umc.study.domain.mapping.Review;
import umc.study.repository.ReviewRepository.ReviewRepository;
import umc.study.repository.StoreRepository.StoreRepository;
import umc.study.repository.UserRepository.UserRepository;
import umc.study.web.dto.ReviewRequestDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Override
    public Review createReview(Long storeId, ReviewRequestDTO.AddReviewDTO request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreHandler(ErrorStatus.STORE_NOT_FOUND));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Review newReview = ReviewConverter.toReview(request, user, store);
        return reviewRepository.save(newReview);
    }
}