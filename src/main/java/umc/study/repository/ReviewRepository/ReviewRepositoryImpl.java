package umc.study.repository.ReviewRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import umc.study.domain.mapping.Review;

import java.util.List;

import static umc.study.domain.mapping.QReview.review;
import static umc.study.domain.QUser.user;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Review> findAllByStoreId(Long storeId, Pageable pageable) {

        List<Review> content = queryFactory
                .selectFrom(review)
                .join(review.user, user).fetchJoin()
                .where(review.store.storeId.eq(storeId))
                .orderBy(review.reviewId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(review.count())
                .from(review)
                .where(review.store.storeId.eq(storeId))
                .fetchOne();

        return new PageImpl<>(content, pageable, (total != null) ? total : 0L);
    }
}