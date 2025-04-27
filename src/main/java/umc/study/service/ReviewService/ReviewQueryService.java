package umc.study.service.ReviewService;

import org.springframework.data.domain.Page;
import umc.study.domain.mapping.Review;

public interface ReviewQueryService {
    Page<Review> getReviewListByStore(Long storeId, int page);
}