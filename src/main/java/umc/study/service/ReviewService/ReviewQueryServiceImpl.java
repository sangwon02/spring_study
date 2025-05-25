package umc.study.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.apiPayload.code.status.ErrorStatus;
import umc.study.apiPayload.exception.handler.StoreHandler;
import umc.study.apiPayload.exception.handler.UserHandler;
import umc.study.domain.mapping.Review;
import umc.study.repository.ReviewRepository.ReviewRepository;
import umc.study.repository.StoreRepository.StoreRepository;
import umc.study.repository.UserRepository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 조회 전용이므로 readOnly = true 설정
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Review> getReviewListByStore(Long storeId, int page) {
        // 가게 존재 여부 확인 (Validator에서 선 검증하지만, 서비스 계층에서도 방어적 확인)
        storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreHandler(ErrorStatus.STORE_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, 10); // 한 페이지에 10개씩
        return reviewRepository.findAllByStoreId(storeId, pageable);
    }

    @Override
    public Page<Review> getReviewListByUser(Long userId, int page) {
        // 사용자 존재 여부 확인 (Validator에서 선 검증하지만, 서비스 계층에서도 방어적 확인)
        userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, 10); // 한 페이지에 10개씩
        return reviewRepository.findAllByUserId(userId, pageable);
    }
}