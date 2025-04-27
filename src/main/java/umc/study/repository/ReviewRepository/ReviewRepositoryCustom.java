package umc.study.repository.ReviewRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.study.domain.mapping.Review;

public interface ReviewRepositoryCustom {

    Page<Review> findAllByStoreId(Long storeId, Pageable pageable);
}